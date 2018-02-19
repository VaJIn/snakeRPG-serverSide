import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.SnakeDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SnakeDAOTest {

    SnakeDAO snakeDAO;
    @Test
    @DisplayName("Test SnakeDAO getSnakeById")
    void testGetSnakeById(){
        Assertions.assertNull(snakeDAO.getSnakeById(-1));
    }

    @Test
    @DisplayName("Test SnakeDAO getSnakeByUser")
    void testGetSnakeByUser(){

    }
}
