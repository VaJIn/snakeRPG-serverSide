package fr.vajin.snakerpg.database.entities;

import java.util.Collection;

public interface GameModeEntity {

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    int getMinPlayer();

    void setMinPlayer(int minPlayer);

    int getMaxPlayer();

    void setMaxPlayer(int maxPlayer);

}
