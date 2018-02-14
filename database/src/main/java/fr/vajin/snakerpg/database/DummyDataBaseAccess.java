package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class DummyDataBaseAccess implements DataBaseAccess {

    private Set<UserEntity> userEntities;
    private Set<SnakeEntity> snakeEntities;
    private Set<GameEntity> gameEntities;
    private Set<GameModeEntity> gameModeEntities;
    private Set<GameParticipationEntity> gameParticipationEntities;
    private Set<SnakeClassEntity> snakeClassEntities;

    public DummyDataBaseAccess() {
        this.userEntities = new HashSet<>();
        this.snakeEntities = new HashSet<>();
        this.gameEntities = new HashSet<>();
        this.gameModeEntities = new HashSet<>();
        this.gameParticipationEntities = new HashSet<>();

        UserEntity u1 = new UserEntity(1, "meluch", "niquelesmediarcarque@fdg.fr", "melanchon2017", "ABABABAB");
        this.userEntities.add(u1);
        UserEntity u2 = new UserEntity(2, "Arny", "pumpingiron@venicebea.ch", "schwarzy", "ABABABAB");
        this.userEntities.add(u2);
        UserEntity u3 = new UserEntity(3, "bowie", "lifeonl@mars.fr", "Dbw", "ABABABAB");
        this.userEntities.add(u3);

        SnakeClassEntity defaultClass = new SnakeClassEntity(1, "default");
        SnakeClassEntity specialClass = new SnakeClassEntity(1, "special");

        snakeClassEntities.add(defaultClass);
        snakeClassEntities.add(specialClass);

        //TODO
    }

    @Override
    public UserEntity getUser(int id) {
        Optional<UserEntity> opt = userEntities.parallelStream().filter(userEntity -> userEntity.getId() == id).findFirst();
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String pseudo) {
        return userEntities.parallelStream()
                .filter(userEntity -> userEntity.getAlias().equals(pseudo))
                .collect(Collectors.toSet());
    }

    @Override
    public SnakeEntity getSnakeById(int id) {
        Optional<SnakeEntity> optional = snakeEntities.parallelStream().filter(snakeEntity -> snakeEntity.getId() == id).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(UserEntity userEntity) {
        return getSnakeByUser(userEntity.getId());
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        return snakeEntities.parallelStream().filter(snakeEntity -> snakeEntity.getUserId() == userId).collect(Collectors.toSet());
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        return null;
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(UserEntity userEntity, int sortBy, Timestamp earliest, Timestamp latest) {
        return null;
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameid, int sortBy) {
        return null;
    }

    @Override
    public Collection<GameParticipationEntity> getGameResultsByGame(GameEntity gameEntity) {
        return null;
    }

    @Override
    public GameEntity getGame(int id) {
        Optional<GameEntity> optional = gameEntities.stream().filter(gameEntity -> gameEntity.getId() == id).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy) {
        return null;
    }

    @Override
    public List<GameEntity> getGameByGamemode(GameModeEntity gameModeEntity, int sortBy) {
        return null;
    }

    @Override
    public GameModeEntity getGameMode(int id) {
        Optional<GameModeEntity> optional = gameModeEntities.stream().filter(gameModeEntity -> gameModeEntity.getId() == id).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public GameModeEntity getGameMode(String name) {
        Optional<GameModeEntity> optional = gameModeEntities.stream().filter(gameModeEntity -> gameModeEntity.getName().equals(name)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Collection<GameModeEntity> getAllGameModes() {
        return gameModeEntities;
    }
}
