package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.*;
import fr.vajin.snakerpg.database.exceptions.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Facade
 * Allow access to the useful information in the database from outside of the database package without knowledges of the framework used.
 */
public interface DataBaseAccess {

    /**
     * Return the user with the given id, or null if none is found
     *
     * @param id the id of the user to retrieve
     * @return the UserEntity of the user matching the given id, or null if none is found
     */
    UserEntity getUser(int id) throws LeJoueurNexistePasCestDommageDuCoupOnLanceUneException;

    UserEntity getUserByAccountName(String accountName);

    UserEntity getUser(String accountName, String password);

    /**
     * Return all user matching the given Pseudo.
     *
     * @param pseudo
     * @return a collection of all users matching the given pseudo
     */
    Collection<UserEntity> getUserByAlias(String pseudo) throws LeJoueurNexistePasCestDommageDuCoupOnLanceUneException;

    /**
     * Return the Snake matching the given id
     *
     * @param id the id of the snake to retrieve
     * @return the SnakeEntity of the snake matching the given id
     */
    SnakeEntity getSnakeById(int id) throws LeSerpentNexistePasCestDommageDuCoupOnLanceUneException;

    /**
     * Return all snake of the given user
     *
     * @param userEntity
     * @return a collection of all snake belonging to the given user.
     */
    Collection<SnakeEntity> getSnakeByUser(UserEntity userEntity) throws LeSerpentNexistePasCestDommageDuCoupOnLanceUneException;

    /**
     * Return all snake for the user with the given id
     *
     * @param userId the id of the user.
     * @return a collection of all snake belonging to the user matching the given id. The collection is null is the user does not exists.
     */
    Collection<SnakeEntity> getSnakeByUser(int userId) throws LeSerpentNexistePasCestDommageDuCoupOnLanceUneException;

    int SORT_BY_EARLIEST_DATE = 0;
    int SORT_BY_LATEST_DATE = 1;
    int SORT_BY_SCORE_ASC = 2;
    int SORT_BY_SCORE_DESC = 3;

    List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) throws UnexistingGameParticipationException;

    List<GameParticipationEntity> getGameResultsByUser(UserEntity userEntity, int sortBy, Timestamp earliest, Timestamp latest) throws UnexistingGameParticipationException;

    List<GameParticipationEntity> getGameResultsByGame(int gameid, int sortBy) throws UnexistingGameParticipationException;

    Collection<GameParticipationEntity> getGameResultsByGame(GameEntity gameEntity, int sortBy) throws UnexistingGameParticipationException;

    GameEntity getGame(int id) throws LaPartieNexistePasOhZutCestVraimentTropNulMaisDuCoupOnEstObligesDeRenvoyerUneExceptionOuAlorsPeutEtreDeRenvoyerNullMaisJePrefereLesExceptions;

    List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy) throws LaPartieNexistePasOhZutCestVraimentTropNulMaisDuCoupOnEstObligesDeRenvoyerUneExceptionOuAlorsPeutEtreDeRenvoyerNullMaisJePrefereLesExceptions;

    List<GameEntity> getGameByGamemode(GameModeEntity gameModeEntity, int sortBy) throws LaPartieNexistePasOhZutCestVraimentTropNulMaisDuCoupOnEstObligesDeRenvoyerUneExceptionOuAlorsPeutEtreDeRenvoyerNullMaisJePrefereLesExceptions;

    GameModeEntity getGameMode(int id) throws TropTristLeModeDeJeuNExistePasObligeDeLancerUneException;

    GameModeEntity getGameMode(String name) throws TropTristLeModeDeJeuNExistePasObligeDeLancerUneException;

    Collection<GameModeEntity> getAllGameModes() throws TropTristLeModeDeJeuNExistePasObligeDeLancerUneException;





}
