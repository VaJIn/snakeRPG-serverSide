package fr.vajin.snakerpg.database;

public interface DAOFactory {

    UserDAO getUserDAO();

    GameDAO getGameDAO();

    GameModeDAO getGameModelDAO();

    GameParticipationDAO getGameParticipationDAO();

    SnakeClassDAO getSnakeClassDAO();

    SnakeDAO getSnakeDAO();
}
