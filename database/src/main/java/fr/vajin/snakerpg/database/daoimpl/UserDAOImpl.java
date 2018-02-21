package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.*;
import java.util.*;

public class UserDAOImpl implements UserDAO {

    private Statement statement;
    private DAOFactory daoFactory;

    public UserDAOImpl(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
        try {
            statement = this.daoFactory.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addUser(UserEntity userEntity) throws SQLException {
        String updateUser = "INSERT INTO User (alias, email, accountName, password) "+
                "VALUES ("+userEntity.getAlias()+", "+userEntity.getEmail()+", "+userEntity.getAccountName()+", "+userEntity.getPassword()+");";

        statement.addBatch(updateUser);

        statement.executeBatch();


    }

    @Override
    public Optional<UserEntity> getUser(int id) {
        String query = "SELECT * " +
                "FROM User " +
                "WHERE id="+id;

        query+=";";
        UserEntity out = null;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                out = resultSetToUserEntity(rs);
            }
        } catch (SQLException e) {
            return null;
        }


        return Optional.ofNullable(out);
    }

    @Override
    public Optional<UserEntity> getUser(String accountName, String hash) {
        String query = "SELECT * " +
                "FROM User " +
                "WHERE accountName='"+accountName+"' AND password='"+hash+"'";

        query+=";";
        UserEntity out = null;

        try{
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()){
                out = resultSetToUserEntity(rs);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(out);
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias) {
        String query = "SELECT * " +
                "FROM User " +
                "WHERE alias='"+alias+"'";

        query+=";";
        Collection<UserEntity> out = new ArrayList<>();

        try {
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

        } catch (SQLException e) {
            return null; //Only if the query is not well formed
        }

        return out;
    }

    @Override
    public Optional<UserEntity> getUserByAccountName(String accountName) {

        String query = "SELECT * " +
                "FROM User " +
                "Where accountName='"+accountName+"'";

        query+=";";
        UserEntity out = null;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()){
                out = resultSetToUserEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(out);
    }

    /**
     * Returns a UserEntity from a ResultSet of the table User
     * @param rs
     * @return
     * @throws SQLException
     */
    private UserEntity resultSetToUserEntity(ResultSet rs) throws SQLException {

        int idGame = rs.getInt("id");
        String alias = rs.getString("alias");
        String email = rs.getString("email");
        String accountName = rs.getString("accountName");
        String password = rs.getString("password");

        return new UserEntity(idGame, alias, email, accountName, password);
    }
}
