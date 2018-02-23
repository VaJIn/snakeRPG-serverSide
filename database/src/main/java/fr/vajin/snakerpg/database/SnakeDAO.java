package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.SnakeEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface SnakeDAO {

    void addSnake(SnakeEntity snakeEntity) throws SQLException;

    Optional<SnakeEntity> getSnakeById(int id);

    Optional<SnakeEntity> getSnakeById(int id, boolean retrieveUserEntity);

    Collection<SnakeEntity> getSnakeByUser(int userId);

    Collection<SnakeEntity> getSnakeByUser(int userId, boolean retrieveUserEntity);
}
