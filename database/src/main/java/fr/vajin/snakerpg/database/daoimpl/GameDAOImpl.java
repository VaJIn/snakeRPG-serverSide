package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import java.sql.*;
import java.util.*;


public class GameDAOImpl implements GameDAO {

    private DAOFactory daoFactory;

    public GameDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

<<<<<<< HEAD
    List<GameEntity> getGameByCondition(String condition, int sortBy, boolean retrieveGameParticipation) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM Game ");
        if (condition != null && !condition.equals("")) {
            queryBuilder.append("WHERE ").append(condition);
        }
        queryBuilder.append(" ").append(sortBy(sortBy)).append(";");
=======
    @Override
    public void addGame(GameEntity gameEntity) throws SQLException {
        String updateGame = "INSERT INTO Game (startTime, endTime, idGameMode) " +
                "VALUES ('"+gameEntity.getStartTime()+"', '"+gameEntity.getEndTime()+"', "+gameEntity.getGameMode().getId()+");";

        Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateGame,Statement.RETURN_GENERATED_KEYS);
        statement.addBatch(updateGame);

        ResultSet keys = statement.getGeneratedKeys();

        int idGame = -1;
        if(keys.next()){
            idGame = keys.getInt(0);
        }
>>>>>>> master

        String query = queryBuilder.toString();

        ResultSet rs = null;
        List<GameEntity> games = new ArrayList<GameEntity>();
        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

<<<<<<< HEAD
            rs = statement.executeQuery(query);
            while (rs.next()) {
                try {
                    GameEntity g = new GameEntity();
                    g.setId(rs.getInt("id"));
                    g.setStartTime(rs.getTimestamp("startTime"));
                    g.setEndTime(rs.getTimestamp("endTime"));
                    int gamemodeId = rs.getInt("idGameMode");
=======
            String updateGameParticipation = "INSERT INTO GameParticipation "+
                    "VALUES ("+idGame+", "+gp.getIdSnake()+", "+gp.getScore()+", "+gp.getKillCount()+", "+gp.getDeathCount()+");";
>>>>>>> master

                    Optional<GameModeEntity> gameModeEntityOptional = daoFactory.getGameModeDAO().getGameMode(gamemodeId);
                    if (gameModeEntityOptional.isPresent()) {
                        g.setGameMode(gameModeEntityOptional.get());
                    } else {
                        g.setGameMode(new GameModeEntity());
                    }

                    games.add(g);
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

        if (retrieveGameParticipation) {
            for (GameEntity g : games) {
                GameParticipationDAO gameParticipationDAO = daoFactory.getGameParticipationDAO();
                g.setParticipationEntitySet(new HashSet<>(gameParticipationDAO.getGameResultsByGame(g.getId(), -1)));
            }
        }

        return games;
    }

    @Override
    public void addGame(GameEntity gameEntity) throws SQLException {
        String updateGame = "INSERT INTO Game (startTime, endTime, idGameMode) " +
                "VALUES ('"+gameEntity.getStartTime()+"', '"+gameEntity.getEndTime()+"', "+gameEntity.getGameMode().getId()+");";

        Connection connection = ConnectionPool.getConnection();
        Statement statement = connection.createStatement();

        statement.executeUpdate(updateGame);

        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {

            int createdId = resultSet.getInt(1);
            gameEntity.setId(createdId);

            for (GameParticipationEntity gp : gameEntity.getParticipationEntitySet()) {
                gp.setIdGame(createdId);
                String updateGameParticipation = "INSERT INTO GameParticipation " +
                        "VALUES (" + gp.getIdGame() + ", " + gp.getIdSnake() + ", " + gp.getScore() + ", " + gp.getKillCount() + ", " + gp.getDeathCount() + ");";

                statement.addBatch(updateGameParticipation);
            }

            statement.executeBatch();
        }

        connection.close();
    }

    @Override
    public Optional<GameEntity> getGame(int id, boolean retrieveGameParticipation) {

        String condition = "id = " + id;

        Collection<GameEntity> result = getGameByCondition(condition, -1, retrieveGameParticipation);


        Iterator<GameEntity> it = result.iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        }
        return Optional.empty();
    }

    @Override
    public Optional<GameEntity> getGame(int id) {
        return getGame(id, true);
    }


    //Si earliest ou latest est null, alors on ignore la condition
    @Override
    public List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy) {
        String condition = "";
        if (earliest != null) {
            condition = "WHERE (startime > '" + earliest + "')";
        }
        if (latest != null) {
            if (condition.equals("")) {
                condition = "WHERE";
            } else {
                condition += " AND";
            }
            condition += " (startime < '" + latest + "')";
        }
        return getGameByCondition(condition, sortBy, true);
    }

    @Override
    public List<GameEntity> getGameByGameMode(GameModeEntity gameModeEntity, int sortBy) {
        String condition = "idGameMode=" + gameModeEntity.getId() + " ";

        return getGameByCondition(condition, sortBy, true);
    }

    private GameEntity resultSetToGameEntity(ResultSet rs) throws SQLException {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+0"));

        int id = rs.getInt("id");
        Timestamp startTime = rs.getTimestamp("startTime", calendar);
        Timestamp endTime = rs.getTimestamp("endTime", calendar);
        int idGameMode = rs.getInt("idGameMode");

        //Paulin 21/02 -> passage par la factory
//        GameModeDAO gameModeDAO = new GameModeDAOImpl(); //Useless
        GameModeDAO gameModeDAO = this.daoFactory.getGameModeDAO();

        //If it's not present there is a problem with the database
        GameModeEntity gameModeEntity = gameModeDAO.getGameMode(idGameMode).get();

        return new GameEntity(id, startTime, endTime, gameModeEntity);
    }

    private String sortBy(int sortBy){
        switch (sortBy){
            case DAOFactory.SORT_BY_EARLIEST_DATE:
                return "ORDER BY startTime";
            case DAOFactory.SORT_BY_LATEST_DATE:
                return "ORDER BY startTime DESC";
            case DAOFactory.SORT_BY_SCORE_ASC:
                return "ORDER BY score";
            case DAOFactory.SORT_BY_SCORE_DESC:
                return "ORDER BY score DESC";
        }
        return "";
    }
}
