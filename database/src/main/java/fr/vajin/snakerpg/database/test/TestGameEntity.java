package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.GamesEntity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TestGameEntity implements GamesEntity {

    int id;
    GameModeEntity gameMode;
    Timestamp startTime;
    Timestamp endTime;
    Set<GameParticipationEntity> gameParticipations;

    public TestGameEntity(int id, GameModeEntity gameMode, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.gameMode = gameMode;
        this.startTime = startTime;
        this.endTime = endTime;
        gameParticipations = new HashSet<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public GameModeEntity getGameMode() {
        return gameMode;
    }

    @Override
    public void setGameMode(GameModeEntity gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public Timestamp getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public Timestamp getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public Collection<GameParticipationEntity> getGameParticipations() {
        return gameParticipations;
    }

    public void setGameParticipations(Collection<GameParticipationEntity> gameParticipations) {
        this.gameParticipations = new HashSet<>(gameParticipations);
        for (GameParticipationEntity gp : this.gameParticipations) {
            gp.setGame(this);
        }
    }

    @Override
    public String toString() {
        return "TestGameEntity{" +
                "id=" + id +
                ", gameMode=" + gameMode +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
