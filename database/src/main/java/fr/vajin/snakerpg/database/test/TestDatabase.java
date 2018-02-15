package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.DataBaseAccess;
import fr.vajin.snakerpg.database.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestDatabase implements DataBaseAccess{

    private Statement statement;

    public TestDatabase(){

        Connection con = null;
        try {
            con = DriverManager.getConnection("dbsnake","symfony","symfony");
            this.statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public UserEntity getUser(int id) {

        String query = "SELECT *" +
                "FROM User" +
                "WHERE id="+id;

        

    }

    @Override
    public Collection<UserEntity> getUserByAlias(String pseudo) {
        return null;
    }

    @Override
    public SnakeEntity getSnakeById(int id) {
        return null;
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        return null;
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        String query = "SELECT gp.idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime, g.idGameMode "
                + "FROM GameParticipation gp "
                + "JOIN (SELECT * FROM Game where (startTime >"+earliest+") AND (startTime <"+latest+") as g "
                + "ON gp.idGame = g.id "
                + "WHERE gp.idSnake in (SELECT id from Snake where userID="+userId+") "
                + sortBy(sortBy);



        ResultSet rs = null;
        List<GameParticipationEntity> gameResults = new ArrayList<GameParticipationEntity>();
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {
                GameParticipationEntity gp = new GameParticipationEntity();
                gp.setIdGame(rs.getInt("idGame"));
                gp.setIdSnake(rs.getInt("idSnake"));
                gp.setScore(rs.getInt("score"));
                gp.setKillCount(rs.getInt("killCount"));
                gp.setDeathCount(rs.getInt("deathCount"));

                GameEntity g = new GameEntity();
                g.setId(rs.getInt("id"));
                g.setStartTime(rs.getTimestamp("startTime"));
                g.setEndTime(rs.getTimestamp("endTime"));

                gp.setGame(g);

                gameResults.add(gp);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameResults;

    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(UserEntity userEntity, int sortBy, Timestamp earliest, Timestamp latest) {
        return getGameResultsByUser(userEntity.getId(),sortBy,earliest,latest);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameid, int sortBy) {
        String query = "SELECT gp.idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime "
                    +"FROM GameParticipation gp "
                    +"JOIN (SELECT id, startTime, endTime FROM Game WHERE id="+gameid+") as g "
                    +"ON gp.idGame=g.id "
                    +sortBy(sortBy);

        ResultSet rs = null;
        List<GameParticipationEntity> gameResults = new ArrayList<GameParticipationEntity>();
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {
                GameParticipationEntity gp = new GameParticipationEntity();
                gp.setIdGame(rs.getInt("idGame"));
                gp.setIdSnake(rs.getInt("idSnake"));
                gp.setScore(rs.getInt("score"));
                gp.setKillCount(rs.getInt("killCount"));
                gp.setDeathCount(rs.getInt("deathCount"));

                GameEntity g = new GameEntity();
                g.setId(rs.getInt("id"));
                g.setStartTime(rs.getTimestamp("startTime"));
                g.setEndTime(rs.getTimestamp("endTime"));

                gp.setGame(g);

                gameResults.add(gp);


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
