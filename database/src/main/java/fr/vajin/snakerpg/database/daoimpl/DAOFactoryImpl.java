package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class DAOFactoryImpl implements DAOFactory{

    private Connection connection;
    private static String db_adr = "jdbc:mysql://localhost:3306/dbsnake";


    public DAOFactoryImpl(){
        try {
            Properties connectionProp = new Properties();
            connectionProp.loadFromXML(getClass().getResourceAsStream("/connection.xml"));
            this.connection = DriverManager.getConnection(db_adr,connectionProp);
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

    public Connection getConnection() {
        return this.connection;
    }
}
