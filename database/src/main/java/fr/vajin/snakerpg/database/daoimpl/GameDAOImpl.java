package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.GameModeDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;


public class GameDAOImpl implements GameDAO {

    private static String db_adr = "jdbc:mysql://localhost:3306/dbsnake";
    private Statement statement;

    public GameDAOImpl(){
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
    public GameEntity getGame(int id) {
        String query = "SELECT * " +
                "FROM Game " +
                "WHERE id="+id;

        query+=";";
        GameEntity out = null;

        try {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                out = resultSetToGameEntity(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out = null;
        }

        return out;
    }

    @Override
    public List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy) {
        String query = "SELECT g.id, g.startTime, g.endTime, g.idGameMode, gm.name, gm.minPlayer, gm.maxPlayer "
                +"FROM Game g "
                +"JOIN (SELECT * FROM GameMode) as gm "
                +"ON g.idGameMode=gm.id "
                +"WHERE (startTime>'"+earliest+"') AND (startTime<'"+latest+"') "
                +sortBy(sortBy);

        ResultSet rs = null;
        List<GameEntity> games= new ArrayList<GameEntity>();
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {

                try {
                    GameEntity g = new GameEntity();
                    g.setId(rs.getInt("id"));
                    g.setStartTime(rs.getTimestamp("startTime"));
                    g.setEndTime(rs.getTimestamp("endTime"));
                    GameModeEntity gm = new GameModeEntity(rs.getInt("idGameMode"), rs.getString("name"), rs.getInt("minPlayer"), rs.getInt("maxPlayer"));
                    g.setGameMode(gm);
                    games.add(g);
                }
                catch (SQLException e){
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    @Override
    public List<GameEntity> getGameByGameMode(GameModeEntity gameModeEntity, int sortBy) {
        String query = "SELECT * FROM Game "
                +"WHERE idGameMode="+gameModeEntity.getId()+" "
                +sortBy(sortBy);

        ResultSet rs = null;
        List<GameEntity> games= new ArrayList<GameEntity>();
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {

                try {
                    GameEntity g = new GameEntity();
                    g.setId(rs.getInt("id"));
                    g.setStartTime(rs.getTimestamp("startTime"));
                    g.setEndTime(rs.getTimestamp("endTime"));
                    g.setGameMode(gameModeEntity);
                    games.add(g);
                }
                catch (SQLException e){
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    private GameEntity resultSetToGameEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        Timestamp startTime = rs.getTimestamp("startTime");
        Timestamp endTime = rs.getTimestamp("endTime");
        int idGameMode = rs.getInt("idGameMode");


        //TODO à vérifier
        GameModeDAO gameModeDAO = new GameModeDAOImpl();

        return new GameEntity(id, startTime, endTime, gameModeDAO.getGameMode(idGameMode));

    }

    private String sortBy(int sortBy){
        switch (sortBy){
            case DAOFactory.SORT_BY_EARLIEST_DATE:
                return "ORDER BY startTime;";
            case DAOFactory.SORT_BY_LATEST_DATE:
                return "ORDER BY startTime DESC;";
            case DAOFactory.SORT_BY_SCORE_ASC:
                return "ORDER BY score;";
            case DAOFactory.SORT_BY_SCORE_DESC:
                return "ORDER BY score DESC;";
        }

        return ";";
    }
}
