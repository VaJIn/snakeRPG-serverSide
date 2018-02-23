package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SnakeDAOImpl implements SnakeDAO {

    private DAOFactory daoFactory;

    public SnakeDAOImpl(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    @Override
    public void addSnake(SnakeEntity snakeEntity) throws SQLException {
        String updateSnake = "INSERT INTO Snake (userID, name, exp, info, idSnakeClass) " +
                "VALUES ('"+snakeEntity.getUser().getId()+"', '"+snakeEntity.getName()+"', "+snakeEntity.getExpPoint()+"+" +
                ", "+snakeEntity.getInfo()+", "+snakeEntity.getSnakeClass().getId()+");";

        Connection connection = ConnectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.addBatch(updateSnake);
        statement.executeBatch();
        connection.close();
    }

    private Collection<SnakeEntity> getSnakeByCondition(String condition, boolean retrieveUserEntity) {
        String query = "SELECT *" +
                " FROM Snake " +
                " WHERE " + condition + ";";

        Collection<SnakeEntity> out = new HashSet<>();

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                try {
                    out.add(resultSetToSnakeEntity(rs));
                }
                catch (SQLException e){
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                    e.printStackTrace();
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (retrieveUserEntity) {
            Map<Integer, UserEntity> userEntityCache = new HashMap<>();

            for (SnakeEntity snakeEntity : out) {
                UserEntity user = userEntityCache.get(snakeEntity.getUserId());
                if (user == null) {
                    Optional<UserEntity> opt = daoFactory.getUserDAO().getUser(snakeEntity.getUserId(), false);
                    if (opt.isPresent()) {
                        user = opt.get();
                        userEntityCache.put(user.getId(), user);
                    }
                }
                snakeEntity.setUser(user);
            }
        }

        return out;
    }

    @Override
    public Optional<SnakeEntity> getSnakeById(int id, boolean retrieveUserEntity) {
        String condition = " id = " + id;

        Collection<SnakeEntity> results = getSnakeByCondition(condition, retrieveUserEntity);

        if (results != null) {
            Iterator<SnakeEntity> it = results.iterator();
            if (it.hasNext()) {
                return Optional.of(it.next());
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<SnakeEntity> getSnakeById(int id) {
        return getSnakeById(id, true);
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId, boolean retrieveUserEntity) {
        String condition = "userId= " + userId;

        return getSnakeByCondition(condition, retrieveUserEntity);
    }


    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        return getSnakeByUser(userId, true);
    }

    private SnakeEntity resultSetToSnakeEntity(ResultSet rs) throws SQLException{

        int id = rs.getInt("id");
        int userId = rs.getInt("userId");
        String name = rs.getString("name");
        int exp = rs.getInt("exp");
        byte[] info = rs.getBytes("info");
        int idSnakeClass = rs.getInt("idSnakeClass");

        SnakeEntity entity = new SnakeEntity();
        entity.setId(id);
        entity.setUserId(userId);
        entity.setName(name);
        entity.setExpPoint(exp);
        entity.setInfo(info);
        entity.setSnakeClassId(idSnakeClass);

        return entity;
    }
}
