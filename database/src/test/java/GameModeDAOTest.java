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
        Assertions.assertNull(gameModeDAO.getGameMode(-1));
        Assertions.assertNotNull(gameModeDAO.getGameMode(1));
    }

    @Test
    @DisplayName("Test GameModeDAO getGameMode(String name)")
    void testGameModeByName(){
        Assertions.assertNull(gameModeDAO.getGameMode(""));
        Assertions.assertNotNull(gameModeDAO.getGameMode("defaultGame"));
    }

}
