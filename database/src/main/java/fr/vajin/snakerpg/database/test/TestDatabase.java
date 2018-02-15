package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.DataBaseAccess;
import fr.vajin.snakerpg.database.entities.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class TestDatabase implements DataBaseAccess{

    private static String db_adr = "jdbc:mysql://localhost:3306/dbsnake";
    private Statement statement;

    public TestDatabase(){

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
                "WHERE id= "+id;

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
    public Collection<UserEntity> getUserByAlias(String pseudo) {

        String query = "SELECT * " +
                "FROM User " +
                "WHERE alias= "+pseudo;

        Collection<UserEntity> out = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                try{
                    out.add(resultSetToUserEntity(rs));
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
    public SnakeEntity getSnakeById(int id) {

        String query = "SELECT *" +
                "FROM Snake " +
                "WHERE id= "+id;

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
    public Collection<SnakeEntity> getSnakeByUser(UserEntity userEntity) {
        return getSnakeByUser(userEntity.getId());
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {

        String query = "SELECT * " +
                "FROM Snake " +
                "WHERE userId= "+userId;

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

    @Override
    public SnakeClassEntity getSnakeClassById(int snakeClassId) {

        String query = "SELECT * " +
                "FROM SnakeClass " +
                "WHERE id= "+snakeClassId;

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

        return out;

    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        String query = "SELECT gp.idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime, g.idGameMode "
                + "FROM GameParticipation gp "
                + "JOIN (SELECT * FROM Game where (startTime >"+earliest+") AND (startTime <"+latest+") as g "
                + "ON gp.idGame  = g.id "
                + "WHERE gp.idSnake in (SELECT id from Snake where userID="+userId+") "
                + sortBy(sortBy);


        return gameParticipationQuery(query);

    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(UserEntity userEntity, int sortBy, Timestamp earliest, Timestamp latest) {
        return getGameResultsByUser(userEntity.getId(),sortBy,earliest,latest);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameid, int sortBy) {
        String query = "SELECT gp.idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime "
                    +"FROM GameParticipation gp "
                    +"JOIN (SELECT id, startTime, endTime, idGameMode FROM Game WHERE id="+gameid+") as g "
                    +"ON gp.idGame=g.id "
                    +sortBy(sortBy);


        return gameParticipationQuery(query);
    }

    private List<GameParticipationEntity> gameParticipationQuery(String query){
        List<GameParticipationEntity> gameResults = new ArrayList<GameParticipationEntity>();
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                try {
                    gameResults.add(resultSetToGameParticipationEntity(rs));
                }
                catch(SQLException e){
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameResults;
    }

    @Override
    public Collection<GameParticipationEntity> getGameResultsByGame(GameEntity gameEntity, int sortBy) {
        return getGameResultsByGame(gameEntity.getId(),sortBy);
    }

    @Override
    public GameEntity getGame(int id) {
        return null;
    }

    @Override
    public List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy) {
        String query = "SELECT g.id, g.startTime, g.endTime, g.idGameMode, gm.name, gm.minPlayer, gm.maxPlayer "
                +"FROM Game g "
                +"JOIN (SELECT * FROM GameMode) as gm "
                +"ON g.idGameMode=gm.id "
                +"WHERE (startTime>"+earliest+") AND (startTime<"+latest+") "
                +sortBy(sortBy);

        ResultSet rs = null;
        List<GameEntity> games= new ArrayList<GameEntity>();
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {
                GameEntity g = new GameEntity();
                g.setId(rs.getInt("id"));
                g.setStartTime(rs.getTimestamp("startTime"));
                g.setEndTime(rs.getTimestamp("endTime"));
                GameModeEntity gm = new GameModeEntity(rs.getInt("idGameMode"),rs.getString("name"),rs.getInt("minPlayer"),rs.getInt("maxPlayer"));

                g.setGameMode(gm);

                games.add(g);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    @Override
    public List<GameEntity> getGameByGamemode(GameModeEntity gameModeEntity, int sortBy) {
        String query = "SELECT * FROM Game "
                +"WHERE idGameMode="+gameModeEntity.getId()+" "
                +sortBy(sortBy);

        ResultSet rs = null;
        List<GameEntity> games= new ArrayList<GameEntity>();
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {
                GameEntity g = new GameEntity();
                g.setId(rs.getInt("id"));
                g.setStartTime(rs.getTimestamp("startTime"));
                g.setEndTime(rs.getTimestamp("endTime"));
                g.setGameMode(gameModeEntity);
                games.add(g);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    @Override
    public GameModeEntity getGameMode(int id) {
        return null;
    }

    @Override
    public GameModeEntity getGameMode(String name) {
        return null;
    }

    @Override
    public Collection<GameModeEntity> getAllGameModes() {
        return null;
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

    private SnakeEntity resultSetToSnakeEntity(ResultSet rs) throws SQLException{

        int id = rs.getInt("id");
        int userId = rs.getInt("userId");
        String name = rs.getString("name");
        int exp = rs.getInt("exp");
        byte[] info = rs.getBytes("info");
        int idSnakeClass = rs.getInt("idSnakeClass");

        return new SnakeEntity(id, name, exp, info, getUser(userId), getSnakeClassById(idSnakeClass));

    }




    private GameEntity resultSetToGameEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        Timestamp startTime = rs.getTimestamp("startTime");
        Timestamp endTime = rs.getTimestamp("endTime");
        int idGameMode = rs.getInt("idGameMode");

        return new GameEntity(id, startTime, endTime, getGameMode(idGameMode));

    }

    private GameModeEntity resultSetToGameModeEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String name = rs.getString("name");
        int minPlayer = rs.getInt("minPlayer");
        int maxPlayer = rs.getInt("maxPlayer");

        return new GameModeEntity(id, name, minPlayer, maxPlayer);
    }

    private GameParticipationEntity resultSetToGameParticipationEntity(ResultSet rs) throws SQLException {

        int idGame = rs.getInt("idGame");
        int idGameMode = rs.getInt("idGameMode");
        int idSnake = rs.getInt("idSnake");
        int score = rs.getInt("score");
        int killCount = rs.getInt("killCount");
        int deathCount = rs.getInt("deathCount");
        Timestamp startTime = rs.getTimestamp("startTime");
        Timestamp endTime = rs.getTimestamp("endTime");

        GameEntity g = new GameEntity(idGame, startTime, endTime, getGameMode(idGameMode));

        return new GameParticipationEntity(idGame, idSnake, score, killCount, deathCount, g, getSnakeById(idSnake));

    }

    private SnakeClassEntity resultSetToSnakeClassEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String name = rs.getString("name");

        return new SnakeClassEntity(id, name);

    }


    private String sortBy(int sortBy){
        switch (sortBy){
            case SORT_BY_EARLIEST_DATE:
                return "ORDER BY startTime;";
            case SORT_BY_LATEST_DATE:
                return "ORDER BY startTime DESC;";
            case SORT_BY_SCORE_ASC:
                return "ORDER BY score;";
            case SORT_BY_SCORE_DESC:
                return "ORDER BY score DESC;";
        }

        return ";";
    }
}
