package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.GameModeDAO;
import fr.vajin.snakerpg.database.entities.GameModeEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class GameModeDAOImpl implements GameModeDAO {

    private static String db_adr = "jdbc:mysql://localhost:3306/dbsnake";
    private Statement statement;

    public GameModeDAOImpl(){
        Connection con = null;
        try {
            Properties connectionProp = new Properties();
            connectionProp.loadFromXML(getClass().getResourceAsStream("/connection.xml"));
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
    public Collection<GameModeEntity> getAllGameMode() {
        String query = "SELECT * " +
                "FROM GameMode";

        query+=";";
        Collection<GameModeEntity> out = new ArrayList<>();


        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){

                try {
                    out.add(resultSetToGameModeEntity(rs));
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

    @Override
    public Optional<GameModeEntity> getGameMode(int id) {
        String query = "SELECT * " +
                "FROM GameMode " +
                "WHERE id="+id;

        query+=";";
        GameModeEntity out = null;

        try {
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()){
                out = resultSetToGameModeEntity(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(out);
    }

    @Override
    public Optional<GameModeEntity> getGameMode(String name) {
        String query = "SELECT * " +
                "FROM GameMode " +
                "WHERE name='"+name+"'";

        query+=";";

        GameModeEntity out = null;

        try {
            ResultSet rs = statement.executeQuery(query);

            if(rs.next()){

                out = resultSetToGameModeEntity(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //out=null;

        }

        return Optional.ofNullable(out);
    }

    private GameModeEntity resultSetToGameModeEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String name = rs.getString("name");
        int minPlayer = rs.getInt("minPlayer");
        int maxPlayer = rs.getInt("maxPlayer");

        return new GameModeEntity(id, name, minPlayer, maxPlayer);
    }
}
