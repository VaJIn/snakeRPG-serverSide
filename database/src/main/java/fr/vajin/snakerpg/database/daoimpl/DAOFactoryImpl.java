package fr.vajin.snakerpg.database.daoimpl;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import fr.vajin.snakerpg.database.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactoryImpl implements DAOFactory{


    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl(this);
    }

    @Override
    public GameDAO getGameDAO() {
        return new GameDAOImpl(this);
    }

    @Override
    public GameModeDAO getGameModeDAO() {
        return new GameModeDAOImpl(this);
    }

    @Override
    public GameParticipationDAO getGameParticipationDAO() {
        return new GameParticipationDAOImpl(this);
    }

    @Override
    public SnakeClassDAO getSnakeClassDAO() {
        return new SnakeClassDAOImpl(this);
    }

    @Override
    public SnakeDAO getSnakeDAO() {
        return new SnakeDAOImpl(this);
    }

}
