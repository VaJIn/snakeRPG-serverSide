import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.daoimpl.DAOFactoryImpl;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
<<<<<<< HEAD
import java.util.Iterator;
=======
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
>>>>>>> master

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

    @Test
    @DisplayName("Test SnakeDAO addSnake")
    void testAddSnake(){

        String name = "nameTestSnake";
        int expPoint = 200;
        byte[] info = null;
        Optional<UserEntity> user = DAOFactoryProvider.getDAOFactory().getUserDAO().getUser(1);
        Optional<SnakeClassEntity> snakeClass = DAOFactoryProvider.getDAOFactory().getSnakeClassDAO().getSnakeClassById(1);

        SnakeEntity snake = new SnakeEntity();
        snake.setName(name);
        snake.setExpPoint(expPoint);
        snake.setInfo(info);
        snake.setUser(user.get());
        snake.setSnakeClass(snakeClass.get());

        Collection<SnakeEntity> snakesBeforeInsert = snakeDAO.getSnakeByUser(user.get().getId());

        Assertions.assertAll(() -> snakeDAO.addSnake(snake));

        Collection<SnakeEntity> snakesAfterInsert = snakeDAO.getSnakeByUser(user.get().getId());


        List<SnakeEntity> newSnakes = snakesAfterInsert.stream().filter(gameEntity1 -> !snakesBeforeInsert.contains(gameEntity1)).collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertTrue(newSnakes.size()==1);

        SnakeEntity newSnake = newSnakes.get(0);

        Assertions.assertEquals(name,newSnake.getName());
        Assertions.assertEquals(expPoint,newSnake.getExpPoint());
        Assertions.assertEquals(info,newSnake.getInfo());
        Assertions.assertEquals(user.get().getId(),newSnake.getUserId());
        Assertions.assertEquals(snakeClass.get().getId(),newSnake.getSnakeClass().getId());

    }
}
