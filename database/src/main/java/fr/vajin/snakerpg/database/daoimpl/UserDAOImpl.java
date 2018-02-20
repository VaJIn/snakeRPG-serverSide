package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class UserDAOImpl implements UserDAO {

    private static String db_adr = "jdbc:mysql://localhost:3306/dbsnake";
    private Statement statement;

    public UserDAOImpl(){
        Connection con = null;
        try {
            Properties connectionProp = new Properties();
            connectionProp.loadFromXML(new FileInputStream(new File("src/test/resources/connection.xml")));
            con = DriverManager.getConnection(db_adr,connectionProp);
            this.statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserEntity getUser(int id) {
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



        return out;
    }

    @Override
    public UserEntity getUser(String accountName, String hash) {
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

        return out;
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
    public UserEntity getUserByAccountName(String accountName) {

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

        return out;
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
