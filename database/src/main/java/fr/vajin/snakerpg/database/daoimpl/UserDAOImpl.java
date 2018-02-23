package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDAOImpl implements UserDAO {

    private DAOFactory daoFactory;

    protected UserDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private Collection<UserEntity> getUserByCondition(String condition, boolean retrieveSnake) {
        String query = "SELECT * " +
                " FROM User " +
                " WHERE " + condition + ";";

        Set<UserEntity> out = new HashSet<>();

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                try{
                    UserEntity user = resultSetToUserEntity(rs);
                    System.out.println(user);
                    out.add(user);
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
            return null; //Only if the query is not well formed
        }
        if (retrieveSnake) {
            for (UserEntity userEntity : out) {
                Collection<SnakeEntity> snakeEntities = daoFactory.getSnakeDAO().getSnakeByUser(userEntity.getId(), false);
                for (SnakeEntity snakeEntity : snakeEntities) {
                    userEntity.addSnake(snakeEntity);
                }
            }
        }

        return out;
    }

    @Override
    public void addUser(UserEntity userEntity) throws SQLException {
        String updateUser = "INSERT INTO User (alias, email, accountName, password) " +
                "VALUES ('" + userEntity.getAlias() + "', '" + userEntity.getEmail() + "', '" + userEntity.getAccountName() + "', '" + userEntity.getPassword() + "');";

        Connection connection = ConnectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.addBatch(updateUser);
        statement.executeBatch();
        connection.close();
    }

    @Override
    public Optional<UserEntity> getUser(int id, boolean retrieveSnake) {

        String condition = "id = " + id;

        Iterator<UserEntity> it = getUserByCondition(condition, retrieveSnake).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> getUser(int id) {
        return getUser(id, true);
    }

    @Override
    public Optional<UserEntity> getUser(String accountName, String hash, boolean retrieveSnake) {
        String condition = "accountName='" + accountName + "' AND password='" + hash + "'";
        Iterator<UserEntity> it = getUserByCondition(condition, retrieveSnake).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> getUser(String accountName, String hash) {
        return getUser(accountName, hash, true);
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias, boolean retrieveSnake) {
        String condition = "alias='" + alias + "'";

        return getUserByCondition(condition, true);
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias) {
        return getUserByAlias(alias, true);
    }

    @Override
    public Optional<UserEntity> getUserByAccountName(String accountName, boolean retrieveSnake) {
        String condition = "accountName='" + accountName + "'";

        Iterator<UserEntity> it = getUserByCondition(condition, true).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> getUserByAccountName(String accountName) {
        return getUserByAccountName(accountName, true);
    }

    /**
     * Returns a UserEntity from a ResultSet of the table User
     * @param rs
     * @return
     * @throws SQLException
     */
    private UserEntity resultSetToUserEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String alias = rs.getString("alias");
        String email = rs.getString("email");
        String accountName = rs.getString("accountName");
        String password = rs.getString("password");

        UserEntity entity = new UserEntity(id, alias, email, accountName, password);

        return entity;
    }
}
