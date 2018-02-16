package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.SnakeEntity;
import java.util.Collection;

public interface SnakeDAO {

    SnakeEntity getSnakeById(int id);

    Collection<SnakeEntity> getSnakeByUser(int userId);

}
