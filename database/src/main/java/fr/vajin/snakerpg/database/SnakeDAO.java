package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.SnakeEntity;
import java.util.Collection;
import java.util.Optional;

public interface SnakeDAO {

    Optional<SnakeEntity> getSnakeById(int id);

    Collection<SnakeEntity> getSnakeByUser(int userId);

}
