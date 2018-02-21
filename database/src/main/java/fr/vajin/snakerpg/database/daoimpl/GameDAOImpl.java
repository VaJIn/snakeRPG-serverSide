package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.GameModeDAO;
import fr.vajin.snakerpg.database.GameParticipationDAO;
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

    @Override
    public void addGame(GameEntity gameEntity) throws SQLException {
        String updateGame = "INSERT INTO Game (startTime, endTime, idGameMode) " +
                "VALUES ('"+gameEntity.getStartTime()+"', '"+gameEntity.getEndTime()+"', "+gameEntity.getGameMode().getId()+");";

        Connection connection = daoFactory.getConnection();
        Statement statement = connection.createStatement();
        statement.addBatch(updateGame);



        for(GameParticipationEntity gp : gameEntity.getParticipationEntitySet()){

            String updateGameParticipation = "INSERT INTO GameParticipation "+
                    "VALUES ("+gp.getIdGame()+", "+gp.getIdSnake()+", "+gp.getScore()+", "+gp.getKillCount()+", "+gp.getDeathCount()+");";

            statement.addBatch(updateGameParticipation);

        }

        statement.executeBatch();

        connection.close();

    }

    @Override
    public Optional<GameEntity> getGame(int id) {
        String query = "SELECT * " +
                "FROM Game " +
                "WHERE id="+id;

        query+=";";

        GameEntity out = null;

        try {
            Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                out = resultSetToGameEntity(rs);
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            out = null;
        }


        if (out == null) {
            return Optional.empty();
        } else {

            GameParticipationDAO gameParticipationDAO = this.daoFactory.getGameParticipationDAO();

            List<GameParticipationEntity> gameParticipationEntityList = gameParticipationDAO.getGameResultsByGame(out.getId(), DAOFactory.SORT_BY_SCORE_ASC);

            out.setParticipationEntitySet(new HashSet<>(gameParticipationEntityList));

            return Optional.of(out);
        }
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

        String query = "SELECT g.id, g.startTime, g.endTime, g.idGameMode, gm.name, gm.minPlayer, gm.maxPlayer "
                +"FROM Game g "
                +"JOIN (SELECT * FROM GameMode) as gm "
                +"ON g.idGameMode=gm.id "
                + condition
                +sortBy(sortBy);

        ResultSet rs = null;
        List<GameEntity> games= new ArrayList<GameEntity>();
        try {
            Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement();

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
                } catch (SQLException e){
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                }

            }

            connection.close();
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
            Connection connection = daoFactory.getConnection();
            Statement statement = connection.createStatement();

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

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
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

    //TODO TEST POUR CETTE FONCTION
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
