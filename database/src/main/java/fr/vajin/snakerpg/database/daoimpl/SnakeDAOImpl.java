package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class SnakeDAOImpl implements SnakeDAO {

    private static String db_adr = "jdbc:mysql://localhost:3306/dbsnake";
    private Statement statement;

    public SnakeDAOImpl(){
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
    public SnakeEntity getSnakeById(int id) {
        String query = "SELECT *" +
                "FROM Snake " +
                "WHERE id="+id;

        query+=";";
        SnakeEntity out = null;

        try {
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                out = resultSetToSnakeEntity(rs);
            }

        } catch (SQLException e) {
            return null;
        }

        return out;
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        String query = "SELECT * " +
                "FROM Snake " +
                "WHERE userId= "+userId;

        query+=";";
        Collection<SnakeEntity> out = new ArrayList<>();

        try {
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

        //TODO à vérifier aussi
        UserDAO userDAO = new UserDAOImpl();
        SnakeClassDAO snakeClassDAO = new SnakeClassDAOImpl();
        return new SnakeEntity(id, name, exp, info, userDAO.getUser(userId), snakeClassDAO.getSnakeClassById(idSnakeClass));

    }
}
