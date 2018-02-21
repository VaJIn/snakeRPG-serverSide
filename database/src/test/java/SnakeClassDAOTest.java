import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.daoimpl.DAOFactoryImpl;
import fr.vajin.snakerpg.database.daoimpl.SnakeClassDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SnakeClassDAOTest {

    DAOFactory daoFactory = new DAOFactoryImpl();
    SnakeClassDAO snakeClassDAO = new SnakeClassDAOImpl(daoFactory);

    @Test
    @DisplayName("Test SnakeClassDAO getSnakeClassById")
    void testGetSnakeClassById(){
        Assertions.assertFalse(snakeClassDAO.getSnakeClassById(-1).isPresent());
        Assertions.assertTrue(snakeClassDAO.getSnakeClassById(1).isPresent());
    }

    @Test
    @DisplayName("Test SnakeClassDAO getAllSnakeClasses")
    void testGetAllSnakeClasses(){
        Assertions.assertNotNull(snakeClassDAO.getAllSnakeClasses());
    }
}
