package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Predicate;
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
        this.snakeClassEntities = new HashSet<>();

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
    public SnakeClassEntity getSnakeClassById(int snakeClassId) {
        return null;
    }

    @Override
    public Collection<GameParticipationEntity> getGameResultsByGame(GameEntity gameEntity, int sortBy) {
        return getGameResultsByGame(gameEntity.getId(), sortBy);
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
    public UserEntity getUser(String accountName, String password) {
        Optional<UserEntity> optional = userEntities.stream().filter(userEntity -> userEntity.getAccountName().equals(accountName) && userEntity.getPassword().equals(password)).findFirst();

        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias) {
        return userEntities.parallelStream()
                .filter(userEntity -> userEntity.getAlias().equals(alias))
                .collect(Collectors.toSet());
    }

    @Override
    public UserEntity getUserByAccountName(String accountName) {
        Optional<UserEntity> optional = userEntities.stream().filter(userEntity -> userEntity.getAccountName().equals(accountName)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
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

    private void sortGameParticipationEntityList(List<GameParticipationEntity> list, int sortBy) {

        Comparator<GameParticipationEntity> comparator;
        switch (sortBy) {
            case SORT_BY_EARLIEST_DATE:
                comparator = (gameParticipationEntity, t1) -> gameParticipationEntity.getGame().getStartTime().compareTo(t1.getGame().getStartTime());
                break;
            case SORT_BY_LATEST_DATE:
                comparator = (gameParticipationEntity, t1) -> t1.getGame().getStartTime().compareTo(gameParticipationEntity.getGame().getStartTime());
                break;
            case SORT_BY_SCORE_ASC:
                comparator = Comparator.comparingInt(GameParticipationEntity::getScore);
                break;
            case SORT_BY_SCORE_DESC:
                comparator = Comparator.comparingInt(GameParticipationEntity::getScore).reversed();
                break;
            default:
                comparator = Comparator.comparingInt(GameParticipationEntity::getScore);
        }

        list.sort(comparator);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        List<GameParticipationEntity> res = new LinkedList<>();

        Predicate<GameParticipationEntity> datePredicate;

        if (earliest != null) {
            if (latest != null) {
                datePredicate = gameParticipationEntity ->
                        gameParticipationEntity.getGame().getStartTime().compareTo(earliest) >= 0
                                && gameParticipationEntity.getGame().getStartTime().compareTo(latest) <= 0;
            } else {
                datePredicate = gameParticipationEntity -> gameParticipationEntity.getGame().getStartTime().compareTo(earliest) <= 0;
            }
        } else if (latest != null) {
            datePredicate = gameParticipationEntity -> gameParticipationEntity.getGame().getEndTime().compareTo(latest) >= 0;
        } else {
            datePredicate = gameParticipationEntity -> true;
        }

        for (SnakeEntity s : getSnakeByUser(userId)) {

            Predicate<GameParticipationEntity> predicate = datePredicate.and(gameParticipationEntity -> gameParticipationEntity.getIdSnake() == s.getId());

            res.addAll(
                    gameParticipationEntities
                            .parallelStream()
                            .filter(predicate)
                            .collect(Collectors.toSet()));
        }

        sortGameParticipationEntityList(res, sortBy);

        return res;
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(UserEntity userEntity, int sortBy, Timestamp earliest, Timestamp latest) {
        return getGameResultsByUser(userEntity.getId(), sortBy, earliest, latest);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameid, int sortBy) {
        List<GameParticipationEntity> res =
                gameParticipationEntities.parallelStream()
                        .filter(gameParticipationEntity -> gameParticipationEntity.getIdGame() == gameid)
                        .collect(Collectors.toList());
        sortGameParticipationEntityList(res, sortBy);
        return res;
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
