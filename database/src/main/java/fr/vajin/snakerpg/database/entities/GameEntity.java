package fr.vajin.snakerpg.database.entities;

import com.google.common.collect.ImmutableSet;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GameEntity {

    private int id;
    private Timestamp startTime;
    private Timestamp endTime;
    private GameModeEntity gameMode;
    private Set<GameParticipationEntity> participationEntitySet;


    public GameEntity() {
        this.id = -1;
        this.startTime = new Timestamp(0);
        this.endTime = new Timestamp(0);
        this.gameMode = new GameModeEntity();
        this.participationEntitySet = new HashSet<>();

    }

    public GameEntity(int id, Timestamp startTime, Timestamp endTime, GameModeEntity gameMode) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gameMode = gameMode;
        this.participationEntitySet = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public GameModeEntity getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameModeEntity gameMode) {
        this.gameMode = gameMode;
    }

    public Set<GameParticipationEntity> getParticipationEntitySet() {
        return ImmutableSet.copyOf(participationEntitySet);
    }

    public void setParticipationEntitySet(Set<GameParticipationEntity> participationEntitySet) {
        this.participationEntitySet = new HashSet<>(participationEntitySet);
        for (GameParticipationEntity gameParticipationEntity : this.participationEntitySet) {
            gameParticipationEntity.setGame(this);
        }
    }

    protected void addGameParticipation(GameParticipationEntity gameParticipationEntity) {
        if (!this.participationEntitySet.contains(gameParticipationEntity)) {
            this.participationEntitySet.add(gameParticipationEntity);
            gameParticipationEntity.setGame(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return id == that.id &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(gameMode, that.gameMode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, startTime, endTime, gameMode);
    }
}
