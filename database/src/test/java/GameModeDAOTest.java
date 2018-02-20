import fr.vajin.snakerpg.database.GameModeDAO;
import fr.vajin.snakerpg.database.daoimpl.GameModeDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameModeDAOTest {

    GameModeDAO gameModeDAO = new GameModeDAOImpl();

    @Test
    @DisplayName("Test GameModeDAO getAllGameMode")
    void testGetAllGameMode(){
        Assertions.assertNotNull(gameModeDAO.getAllGameMode());
    }

    @Test
    @DisplayName("Test GameModeDAO getGameMode(int id)")
    void testGetGameModeById(){
        Assertions.assertFalse(gameModeDAO.getGameMode(-1).isPresent());
        Assertions.assertTrue(gameModeDAO.getGameMode(1).isPresent());
    }

    @Test
    @DisplayName("Test GameModeDAO getGameMode(String name)")
    void testGameModeByName(){
        Assertions.assertFalse(gameModeDAO.getGameMode("").isPresent());
        Assertions.assertTrue(gameModeDAO.getGameMode("defaultGame").isPresent());
    }

}
