package fr.vajin.snakerpg.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAOFactory {

    int SORT_BY_EARLIEST_DATE = 0;
    int SORT_BY_LATEST_DATE = 1;
    int SORT_BY_SCORE_ASC = 2;
    int SORT_BY_SCORE_DESC = 3;


    UserDAO getUserDAO();

    GameDAO getGameDAO();

    GameModeDAO getGameModeDAO();

    GameParticipationDAO getGameParticipationDAO();

    SnakeClassDAO getSnakeClassDAO();

    SnakeDAO getSnakeDAO();

    Connection getConnection() throws SQLException;
}
