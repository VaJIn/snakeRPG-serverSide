import fr.vajin.snakerpg.database.GameDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameDAOTest {

    GameDAO gameDAO;

    @Test
    @DisplayName("Test GameDAO getGame(int id)")
    void testGetGame(){

        Assertions.assertNull(gameDAO.getGame(-1));

    }

    @Test
    @DisplayName("Test GameDAO getGameByDate")
    void testGetGameByDate(){


    }

    @Test
    @DisplayName("Test GameDAO getGameByMode")
    void testGetGameByGameMode(){

    }
}
