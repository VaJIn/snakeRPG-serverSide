import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntitiesTest {

    @Test
    void testUserSnake() {
        UserEntity user = new UserEntity(1, "alias1", "user1@domain.fr", "user1", "123456");
        SnakeEntity snakeEntity = new SnakeEntity();
        snakeEntity.setName("snake1");
        snakeEntity.setId(1);
        snakeEntity.setUser(user);

        Assertions.assertTrue(user.getSnakes().contains(snakeEntity));
        Assertions.assertTrue(snakeEntity.getUser() == user);
    }
}
