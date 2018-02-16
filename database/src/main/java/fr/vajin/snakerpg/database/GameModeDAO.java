package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.GameModeEntity;

import java.util.Collection;

public interface GameModeDAO {

    /**
     * @return a collection with all the gameMode
     */
    Collection<GameModeEntity> getAllGameMode();

    /**
     * Retrieve the gamemode the with given id
     *
     * @param id the id of the gamemode to retrieve.
     * @return the gamemode with the given id, or null if none is found.
     */
    GameModeEntity getGameMode(int id);

    /**
     * Retrieve the gamemode with the given name, or null if none is found.
     *
     * @param name the name of the gamemode to retrieve.
     * @return the gamemode with the given name, or null if none is found.
     */
    GameModeEntity getGameMode(String name);
}
