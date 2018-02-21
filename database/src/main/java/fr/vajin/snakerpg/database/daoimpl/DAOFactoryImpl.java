package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;

public class DAOFactoryImpl implements DAOFactory{


    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    @Override
    public GameDAO getGameDAO() {
        return new GameDAOImpl();
    }

    @Override
    public GameModeDAO getGameModeDAO() {
        return new GameModeDAOImpl();
    }

    @Override
    public GameParticipationDAO getGameParticipationDAO() {
        return new GameParticipationDAOImpl();
    }

    @Override
    public SnakeClassDAO getSnakeClassDAO() {
        return new SnakeClassDAOImpl();
    }

    @Override
    public SnakeDAO getSnakeDAO() {
        return new SnakeDAOImpl();
    }
}
