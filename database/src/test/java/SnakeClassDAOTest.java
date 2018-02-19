import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.daoimpl.SnakeClassDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SnakeClassDAOTest {

    SnakeClassDAO snakeClassDAO = new SnakeClassDAOImpl();

    @Test
    @DisplayName("Test SnakeClassDAO getSnakeClassById")
    void testGetSnakeClassById(){
        Assertions.assertNull(snakeClassDAO.getSnakeClassById(-1));
        Assertions.assertNotNull(snakeClassDAO.getSnakeClassById(1));
    }

    @Test
    @DisplayName("Test SnakeClassDAO getAllSnakeClasses")
    void testGetAllSnakeClasses(){
        Assertions.assertNotNull(snakeClassDAO.getAllSnakeClasses());
    }
}
