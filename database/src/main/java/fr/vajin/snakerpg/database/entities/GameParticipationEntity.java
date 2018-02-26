package fr.vajin.snakerpg.database.entities;

import java.util.Objects;

public class GameParticipationEntity {

    private int idSnake;
    private int idGame;
    private int score;
    private int killCount;
    private int deathCount;
    private GameEntity game;
    private SnakeEntity snake;

    public GameParticipationEntity(){
        this.idSnake = -1;
        this.idGame = -1;
        this.score = -1;
        this.killCount = -1;
        this.deathCount = -1;
        this.game = new GameEntity();
        this.snake = new SnakeEntity();
    }

    public GameParticipationEntity(int idSnake, int idGame, int score, int killCount, int deathCount, GameEntity game, SnakeEntity snake) {
        this.idSnake = idSnake;
        this.idGame = idGame;
        this.score = score;
        this.killCount = killCount;
        this.deathCount = deathCount;
        this.game = game;
        this.snake = snake;
    }

    public int getIdSnake() {
        return idSnake;
    }

    public void setIdSnake(int idSnake) {
        this.idSnake = idSnake;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public GameEntity getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "GameParticipationEntity{" +
                "idSnake=" + idSnake +
                ", idGame=" + idGame +
                ", score=" + score +
                ", killCount=" + killCount +
                ", deathCount=" + deathCount +
                '}';
    }

    public void setGame(GameEntity game) {
        if (this.game != game) {
            this.game = game;
            this.idGame = game.getId();
            if (!game.getParticipationEntitySet().contains(this)) {
                game.addGameParticipation(this);
            }
        }
    }

    public SnakeEntity getSnake() {
        return snake;
    }

    public void setSnake(SnakeEntity snake) {
        this.snake = snake;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameParticipationEntity that = (GameParticipationEntity) o;
        return idSnake == that.idSnake &&
                idGame == that.idGame &&
                score == that.score &&
                killCount == that.killCount &&
                deathCount == that.deathCount &&
                Objects.equals(game, that.game) &&
                Objects.equals(snake, that.snake);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idSnake, idGame, score, killCount, deathCount, game, snake);
    }
}