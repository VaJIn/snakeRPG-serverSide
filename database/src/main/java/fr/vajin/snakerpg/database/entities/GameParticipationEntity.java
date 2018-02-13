package fr.vajin.snakerpg.database.entities;

public interface GameParticipationEntity {

    int getIdSnake();

    void setIdSnake(int idSnake);

    int getIdGame();

    void setIdGame(int idGame);

    int getScore();

    void setScore(int score);

    int getKillCount();

    void setKillCount(int kill);

    int getDeathCount();

    void setDeathCount(int death);

    SnakeEntity getSnake();

    void setSnake(SnakeEntity snakes);

    GamesEntity getGame();

    void setGame(GamesEntity games);
}