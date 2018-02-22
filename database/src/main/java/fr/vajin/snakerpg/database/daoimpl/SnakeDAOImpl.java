package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;

import java.sql.*;
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

    @Override
    public Optional<SnakeEntity> getSnakeById(int id) {
        String query = "SELECT *" +
                "FROM Snake " +
                "WHERE id="+id;

        query+=";";
        SnakeEntity out = null;

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                out = resultSetToSnakeEntity(rs);
            }
            connection.close();
        } catch (SQLException e) {
            return null;
        }

        return Optional.ofNullable(out);
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        String query = "SELECT * " +
                "FROM Snake " +
                "WHERE userId= "+userId;

        query+=";";
        Collection<SnakeEntity> out = new ArrayList<>();

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

        return out;
    }

    private SnakeEntity resultSetToSnakeEntity(ResultSet rs) throws SQLException{

        int id = rs.getInt("id");
        int userId = rs.getInt("userId");
        String name = rs.getString("name");
        int exp = rs.getInt("exp");
        byte[] info = rs.getBytes("info");
        int idSnakeClass = rs.getInt("idSnakeClass");

        UserDAO userDAO = this.daoFactory.getUserDAO();
        SnakeClassDAO snakeClassDAO = this.daoFactory.getSnakeClassDAO();
        return new SnakeEntity(id, name, exp, info, userDAO.getUser(userId).get(), snakeClassDAO.getSnakeClassById(idSnakeClass).orElse(new SnakeClassEntity()));
    }
}
