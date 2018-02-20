package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SnakeClassDAOImpl implements SnakeClassDAO {

    private static String db_adr = "jdbc:mysql://localhost:3306/dbsnake";
    private Statement statement;

    public SnakeClassDAOImpl(){
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
    public Optional<SnakeClassEntity> getSnakeClassById(int snakeClassId) {
        String query = "SELECT * " +
                "FROM SnakeClass " +
                "WHERE id="+snakeClassId;

        query+=";";
        SnakeClassEntity out = null;

        try {
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                out = resultSetToSnakeClassEntity(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out = null;
        }

        return Optional.ofNullable(out);
    }

    @Override
    public Collection<SnakeClassEntity> getAllSnakeClasses() {
        String query = "SELECT * "+
                "FROM SnakeClass;";

        Collection<SnakeClassEntity> out = new ArrayList<>();


        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){

                try {
                    out.add(resultSetToSnakeClassEntity(rs));
                }
                catch (SQLException e){
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }

    private SnakeClassEntity resultSetToSnakeClassEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String name = rs.getString("name");

        return new SnakeClassEntity(id, name);

    }
}
