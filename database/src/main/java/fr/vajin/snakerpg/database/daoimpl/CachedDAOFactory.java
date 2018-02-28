package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;

import java.util.concurrent.TimeUnit;

public class CachedDAOFactory implements DAOFactory {

    private UserDAO userDAO;
    private GameDAO gameDAO;
    private SnakeDAO snakeDAO;
    private GameParticipationDAO gameParticipationDAO;
    private GameModeDAO gameModeDAO;
    private SnakeClassDAO snakeClassDAO;


    public CachedDAOFactory() {
        this.userDAO = new CachedUserDAO(new UserDAOImpl(this), this, 100, 10, TimeUnit.MINUTES);
        this.snakeDAO = new CachedSnakeDAO(new SnakeDAOImpl(this), this, 100, 10, TimeUnit.MINUTES);

        this.gameModeDAO = new CachedGameModeDAO(new GameModeDAOImpl(this), this);

        //TODO cached DAO for all other dao
        this.gameDAO = new GameDAOImpl(this);
        this.snakeClassDAO = new SnakeClassDAOImpl(this);
        this.gameParticipationDAO = new GameParticipationDAOImpl(this);
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public GameDAO getGameDAO() {
        return gameDAO;
    }

    @Override
    public SnakeDAO getSnakeDAO() {
        return snakeDAO;
    }

    @Override
    public GameParticipationDAO getGameParticipationDAO() {
        return gameParticipationDAO;
    }

    @Override
    public GameModeDAO getGameModeDAO() {
        return gameModeDAO;
    }

    @Override
    public SnakeClassDAO getSnakeClassDAO() {
        return snakeClassDAO;
    }
}
