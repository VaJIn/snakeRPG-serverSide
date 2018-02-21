package fr.vajin.snakerpg.database.daoimpl;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import fr.vajin.snakerpg.database.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactoryImpl implements DAOFactory{

    private BoneCP connectionPool;


    public DAOFactoryImpl(){

        Properties connectionProp = new Properties();
        try {
            connectionProp.loadFromXML(getClass().getResourceAsStream("/connection.xml"));
            BoneCPConfig config = new BoneCPConfig();

            config.setJdbcUrl(connectionProp.getProperty("url"));
            config.setUsername(connectionProp.getProperty("user"));
            config.setPassword(connectionProp.getProperty("password"));

            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(2);

            connectionPool = new BoneCP(config);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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

    public Connection getConnection() throws SQLException {
        return this.connectionPool.getConnection();
    }
}
