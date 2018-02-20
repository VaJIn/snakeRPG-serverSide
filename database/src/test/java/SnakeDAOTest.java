import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.daoimpl.SnakeDAOImpl;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

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
        Collection<SnakeEntity> snakes= snakeDAO.getSnakeByUser(-1);
        Assertions.assertNotNull(snakes);
        Assertions.assertEquals(0,snakes.size());
        Assertions.assertNotNull(snakeDAO.getSnakeByUser(1));
    }
}
