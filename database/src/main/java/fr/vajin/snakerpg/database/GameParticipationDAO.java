package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import java.sql.Timestamp;
import java.util.List;

public interface GameParticipationDAO {

    List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest);

    List<GameParticipationEntity> getGameResultsByGame(int gameId, int sortBy);

    List<GameParticipationEntity> getGameParticipationByIds(int gameId, int snakeId, int sortBy);
}
