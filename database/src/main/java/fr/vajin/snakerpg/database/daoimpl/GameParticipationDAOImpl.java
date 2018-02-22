package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GameParticipationDAOImpl implements GameParticipationDAO {

    private DAOFactory daoFactory;


    public GameParticipationDAOImpl(DAOFactory daoFactory){
        this.daoFactory = daoFactory;


    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        String query = "SELECT gp.idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime, g.idGameMode "
                + "FROM GameParticipation gp "
                + "JOIN (SELECT * FROM Game WHERE (startTime >'"+earliest+"') AND (startTime <'"+latest+"')) as g "
                + "ON gp.idGame  = g.id "
                + "WHERE gp.idSnake in (SELECT id from Snake where userID="+userId+") "
                + sortBy(sortBy);


        return gameParticipationQuery(query);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameId, int sortBy) {
        String query = "SELECT gp.idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime, g.idGameMode "
                +"FROM GameParticipation gp "
                +"JOIN (SELECT id, startTime, endTime, idGameMode FROM Game WHERE id="+gameId+") as g "
                +"ON gp.idGame=g.id "
                +sortBy(sortBy);


        return gameParticipationQuery(query);
    }

    @Override
    public List<GameParticipationEntity> getGameParticipationByIds(int gameId, int snakeId, int sortBy) {
        String query = "SELECT gp.idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime, g.idGameMode "
                +"FROM GameParticipation gp "
                +"JOIN (SELECT id, startTime, endTime, idGameMode FROM Game WHERE id="+gameId+") as g "
                +"ON gp.idGame=g.id "
                +"WHERE gp.idSnake="+snakeId+" "
                +sortBy(sortBy);


        return gameParticipationQuery(query);
    }

    private List<GameParticipationEntity> gameParticipationQuery(String query){
        List<GameParticipationEntity> gameResults = new ArrayList<GameParticipationEntity>();
        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

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

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameResults;
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


        //TODO à vérifier aussi
        GameModeDAO gameModeDAO = this.daoFactory.getGameModeDAO();
        SnakeDAO snakeDAO = this.daoFactory.getSnakeDAO();
        GameEntity g = new GameEntity(idGame, startTime, endTime, gameModeDAO.getGameMode(idGameMode).get());

        return new GameParticipationEntity(idGame, idSnake, score, killCount, deathCount, g, snakeDAO.getSnakeById(idSnake).get());

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
