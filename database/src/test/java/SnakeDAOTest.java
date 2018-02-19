import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.daoimpl.SnakeDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SnakeDAOTest {

    SnakeDAO snakeDAO = new SnakeDAOImpl();

    @Test
    @DisplayName("Test SnakeDAO getSnakeById")
    void testGetSnakeById(){
        Assertions.assertNull(snakeDAO.getSnakeById(-1));
        Assertions.assertNotNull(snakeDAO.getSnakeById(1));
    }

    @Test
    @DisplayName("Test SnakeDAO getSnakeByUser")
    void testGetSnakeByUser(){
        Assertions.assertNull(snakeDAO.getSnakeByUser(-1));
        Assertions.assertNotNull(snakeDAO.getSnakeByUser(1));
    }
}
