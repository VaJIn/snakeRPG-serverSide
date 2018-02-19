import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.daoimpl.GameDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class GameDAOTest {

    GameDAO gameDAO = new GameDAOImpl();

    @Test
    @DisplayName("Test GameDAO getGame(int id)")
    void testGetGame(){

        Assertions.assertNull(gameDAO.getGame(-1));
        Assertions.assertNotNull(gameDAO.getGame(1));
    }

    @Test
    @DisplayName("Test GameDAO getGameByDate")
    void testGetGameByDate(){

        Assertions.assertNull(gameDAO.getGameByDate(null,null,0));
        Assertions.assertNotNull(gameDAO.getGameByDate(new Timestamp(0),new Timestamp(0),0));


    }

    @Test
    @DisplayName("Test GameDAO getGameByMode")
    void testGetGameByGameMode(){
        Assertions.assertNull(gameDAO.getGameByGameMode(null,0));
    }
}
