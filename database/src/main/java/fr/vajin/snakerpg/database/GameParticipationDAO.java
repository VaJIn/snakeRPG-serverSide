package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface GameParticipationDAO {

    List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest);

    List<GameParticipationEntity> getGameResultsByGame(int gameId, int sortBy, boolean retrieveGameEntity);
    List<GameParticipationEntity> getGameResultsByGame(int gameId, int sortBy);

    Optional<GameParticipationEntity> getGameParticipationByIds(int gameId, int snakeId, int sortBy);

    List<GameParticipationEntity> getGameParticipation(Timestamp earliest, Timestamp lastest, int sortBy);
}
