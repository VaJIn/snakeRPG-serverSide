package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.GamesEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;

public class TestGameParticipationEntity implements GameParticipationEntity {

    private SnakeEntity snake;
    private GamesEntity game;
    private int score;
    private int deathCount;
    private int killCount;

    public TestGameParticipationEntity() {
    }

    public TestGameParticipationEntity(SnakeEntity snake, GamesEntity game, int score, int deathCount, int killCount) {
        this.snake = snake;
        this.game = game;
        this.score = score;
        this.deathCount = deathCount;
        this.killCount = killCount;
    }

    @Override
    public int getIdSnake() {
        return snake.getId();
    }

    @Override
    public void setIdSnake(int idSnake) {

    }

    @Override
    public int getIdGame() {
        return game.getId();
    }

    @Override
    public void setIdGame(int idGame) {

    }

    @Override
    public SnakeEntity getSnake() {
        return snake;
    }

    @Override
    public void setSnake(SnakeEntity snake) {
        if (this.snake != snake) {
            this.snake = snake;
            snake.getGameParticipations().add(this);
        }
    }

    @Override
    public GamesEntity getGame() {
        return game;
    }

    @Override
    public void setGame(GamesEntity game) {
        if (this.game != game) {
            this.game = game;
            game.getGameParticipations().add(this);
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getDeathCount() {
        return deathCount;
    }

    @Override
    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    @Override
    public int getKillCount() {
        return killCount;
    }

    @Override
    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }
}
