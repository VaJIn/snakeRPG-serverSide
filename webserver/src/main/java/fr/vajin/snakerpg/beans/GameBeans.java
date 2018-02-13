package fr.vajin.snakerpg.beans;

import java.time.LocalDateTime;

public class GameBeans {

    int id;
    String gameMode;
    LocalDateTime startTime;
    LocalDateTime endTime;

    public GameBeans() {
        this.id = -1;
        this.gameMode = "Undefined";
        startTime = LocalDateTime.now();
        endTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
