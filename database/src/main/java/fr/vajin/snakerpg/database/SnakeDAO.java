package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.SnakeEntity;
import java.util.Collection;
import java.util.Optional;

public interface SnakeDAO {

    void addSnake(SnakeEntity snakeEntity);

    Optional<SnakeEntity> getSnakeById(int id);

    Collection<SnakeEntity> getSnakeByUser(int userId);

}
