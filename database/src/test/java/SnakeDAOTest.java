import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

public class SnakeDAOTest {

    SnakeDAO snakeDAO = DAOFactoryProvider.getDAOFactory().getSnakeDAO();

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

        snakes = snakeDAO.getSnakeByUser(1);
        Assertions.assertNotNull(snakes);
        Assertions.assertEquals(3, snakes.size());

        Iterator<SnakeEntity> it = snakes.iterator();

        UserEntity user = it.next().getUser();
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getId());
        while (it.hasNext()) {
            UserEntity user2 = it.next().getUser();
            Assertions.assertNotNull(user2);
            Assertions.assertTrue(user == user2);
        }
    }
}
