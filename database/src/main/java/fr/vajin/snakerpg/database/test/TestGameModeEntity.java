package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.entities.GameModeEntity;

public enum TestGameModeEntity implements GameModeEntity {

    SINGLE_PLAYER(0, "Single Player", 1, 1),
    CLASSIC_DM(1, "Classic Deathmatch", 2, 8);


    private int id;
    private String name;
    private int minPlayer;
    private int maxPlayer;

    TestGameModeEntity(int id, String name, int minPlayer, int maxPlayer) {
        this.id = id;
        this.name = name;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getMinPlayer() {
        return minPlayer;
    }

    @Override
    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
    }

    @Override
    public int getMaxPlayer() {
        return maxPlayer;
    }

    @Override
    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }
}
