package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;

import java.sql.Timestamp;
import java.util.List;

public interface GameDAO {

    GameEntity getGame(int id);

    List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy);

    List<GameEntity> getGameByGameMode(GameModeEntity gameModeEntity, int sortBy);

}
