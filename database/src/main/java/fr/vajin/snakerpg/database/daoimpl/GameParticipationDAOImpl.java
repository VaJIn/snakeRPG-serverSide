package fr.vajin.snakerpg.database.daoimpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


public class GameParticipationDAOImpl implements GameParticipationDAO {

    private DAOFactory daoFactory;


    public GameParticipationDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private String getTimeCondition(Timestamp earliest, Timestamp latest) {
        String timeCondition = "";
        if (earliest != null) {
            timeCondition += "WHERE ( startTime >'" + earliest + "')";
            if (latest != null) {
                timeCondition += " AND ";
            }
        }
        if (latest != null) {
            if (earliest == null) {
                timeCondition += "WHERE ";
            }
            timeCondition += "( startTime < '" + latest + "')";
        }
        return timeCondition;
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest, int startIndex, int count) {

        String timeCondition = getTimeCondition(earliest, latest);

        String query = "SELECT gp.idGame idGame, gp.idSnake, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime, g.idGameMode "
                + "FROM GameParticipation gp "
                + "JOIN (SELECT * FROM Game " + timeCondition + ") as g "
                + "ON gp.idGame  = g.id "
                + "WHERE gp.idSnake in (SELECT id from Snake where userID=" + userId + ") "
                + sortBy(sortBy);

        if(startIndex>-1){
            query+="LIMIT "+startIndex+","+count;
        }

        query +=";";

        return gameParticipationQuery(query, true, true);
    }


    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        return getGameResultsByUser(userId,sortBy,earliest,latest,-1,0);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameId, int sortBy) {
        return getGameResultsByGame(gameId, sortBy, true);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameId, int sortBy, boolean retrieveGame) {
        String query = "SELECT gp.idGame idGame, gp.idSnake idSnake, gp.score score, gp.killCount killCount, gp.deathCount deathCount\n"
                + "FROM GameParticipation gp "
                + "JOIN Game g "
                + "ON gp.idGame  = g.id \n"
                + "WHERE gp.idGame = " + gameId + " "
                + sortBy(sortBy)+";";


        return gameParticipationQuery(query, retrieveGame, true);
    }

    @Override
    public Optional<GameParticipationEntity> getGameParticipationByIds(int gameId, int snakeId, int sortBy) {
        String query = "SELECT gp.idGame idGame, gp.idSnake idSnake, gp.score score, gp.killCount killCount, gp.deathCount deathCount\n"
                + "FROM GameParticipation gp "
                + "JOIN Game g ON gp.idGame  = g.id \n"
                + "WHERE idSnake=" + snakeId + " AND idGame=" + gameId + " "
                + sortBy(sortBy)+";";

        Iterator<GameParticipationEntity> it = gameParticipationQuery(query, true, true).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<GameParticipationEntity> getGameParticipation(Timestamp earliest, Timestamp latest, int sortBy, int startIndex, int count) {
        String condition = getTimeCondition(earliest, latest);

        String query = "SELECT gp.idGame idGame, gp.idSnake idSnake, gp.score score, gp.killCount killCount, gp.deathCount deathCount\n"
                + "FROM GameParticipation gp "
                + "JOIN Game g ON gp.idGame  = g.id \n"
                + condition
                + sortBy(sortBy);

        if(startIndex>-1){
            query+="LIMIT "+startIndex+","+count;
        }

        query+=";";
        return gameParticipationQuery(query, true, true);

    }

    @Override
    public List<GameParticipationEntity> getGameParticipation(Timestamp earliest, Timestamp latest, int sortBy) {

        return getGameParticipation(earliest,latest,sortBy,-1,0);

    }

    private List<GameParticipationEntity> gameParticipationQuery(String query, boolean retrieveGameEntity, boolean retrieveSnake) {
        List<GameParticipationEntity> gameResults = new ArrayList<>();
        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                try {
                    gameResults.add(resultSetToGameParticipationEntity(rs));
                } catch (SQLException e) {
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                    e.printStackTrace();
                }

            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (retrieveGameEntity || retrieveSnake) {
            LoadingCache<Integer, Optional<GameEntity>> gameCache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, Optional<GameEntity>>() {
                @Override
                public Optional<GameEntity> load(Integer key) throws Exception {
                    return daoFactory.getGameDAO().getGame(key, false);
                }
            });
            LoadingCache<Integer, Optional<SnakeEntity>> snakeCache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, Optional<SnakeEntity>>() {
                @Override
                public Optional<SnakeEntity> load(Integer key) throws Exception {
                    return daoFactory.getSnakeDAO().getSnakeById(key);
                }
            });
            for (GameParticipationEntity gp : gameResults) {
                if (retrieveGameEntity) {
                    try {
                        gameCache.get(gp.getIdGame()).ifPresent(gameEntity -> gp.setGame(gameEntity));
                    } catch (ExecutionException e) {
                    }
                }
                if (retrieveSnake) {
                    try {
                        snakeCache.get(gp.getIdSnake()).ifPresent(snakeEntity -> gp.setSnake(snakeEntity));
                    } catch (ExecutionException e) {
                    }
                }
            }
        }

        return gameResults;
    }

    private GameParticipationEntity resultSetToGameParticipationEntity(ResultSet rs) throws SQLException {

        int idGame = rs.getInt("idGame");
        int idSnake = rs.getInt("idSnake");
        int score = rs.getInt("score");
        int killCount = rs.getInt("killCount");
        int deathCount = rs.getInt("deathCount");

        GameParticipationEntity entity = new GameParticipationEntity();

        entity.setIdGame(idGame);
        entity.setIdSnake(idSnake);
        entity.setDeathCount(deathCount);
        entity.setKillCount(killCount);
        entity.setScore(score);

        return entity;
    }

    private String sortBy(int sortBy) {
        switch (sortBy) {
            case DAOFactory.SORT_BY_EARLIEST_DATE:
                return "ORDER BY g.startTime ";
            case DAOFactory.SORT_BY_LATEST_DATE:
                return "ORDER BY g.startTime DESC ";
            case DAOFactory.SORT_BY_SCORE_ASC:
                return "ORDER BY score ";
            case DAOFactory.SORT_BY_SCORE_DESC:
                return "ORDER BY score DESC ";
        }
        return " ";
    }
}
