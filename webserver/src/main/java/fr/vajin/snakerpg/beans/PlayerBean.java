package fr.vajin.snakerpg.beans;

import java.time.LocalDateTime;

public class PlayerBean {

    private int id;
    private String name;
    private int bestScore;
    private LocalDateTime bestScoreDate;

    public PlayerBean() {

    }

    public PlayerBean(int id, String name, int bestScore, LocalDateTime bestScoreDate) {
        super();
        this.id = id;
        this.name = name;
        this.bestScore = bestScore;
        this.bestScoreDate = bestScoreDate;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public LocalDateTime getBestScoreDate() {
        return this.bestScoreDate;
    }

    public void setBestScoreDate(LocalDateTime bestScoreDate) {
        this.bestScoreDate = bestScoreDate;
    }
}
