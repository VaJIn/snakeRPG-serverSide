package fr.vajin.snakerpg.database.entities;

import java.sql.Timestamp;
import java.util.Collection;

public interface GamesEntity {

    int getId();

    void setId(int id);

    Timestamp getStartTime();

    void setStartTime(Timestamp startTime);

    Timestamp getEndTime();

    void setEndTime(Timestamp endTime);

    Collection<GameParticipationEntity> getGameParticipations();

    void setGameParticipations(Collection<GameParticipationEntity> gameParticipations);

    GameModeEntity getGameMode();

    void setGameMode(GameModeEntity gameMode);
}
