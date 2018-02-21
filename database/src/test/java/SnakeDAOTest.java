import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.daoimpl.DAOFactoryImpl;
import fr.vajin.snakerpg.database.daoimpl.SnakeDAOImpl;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class SnakeDAOTest {

    DAOFactory daoFactory = new DAOFactoryImpl();
    SnakeDAO snakeDAO = new SnakeDAOImpl(daoFactory);

    @Test
    @DisplayName("Test SnakeDAO getSnakeById")
    void testGetSnakeById(){
        Assertions.assertFalse(snakeDAO.getSnakeById(-1).isPresent());

        Assertions.assertTrue(snakeDAO.getSnakeById(1).isPresent());
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
