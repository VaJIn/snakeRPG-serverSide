package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.SnakeClassEntity;

import java.util.Collection;

public interface SnakeClassDAO {

    SnakeClassEntity getSnakeClassById(int snakeClassId);

    Collection<SnakeClassEntity> getAllSnakeClasses();
}
