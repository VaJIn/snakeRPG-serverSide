package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.DataBaseAccess;
import fr.vajin.snakerpg.database.entities.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestDataBaseAccess implements DataBaseAccess {


    Set<UserEntity> userEntitySet;
    Set<SnakeEntity> snakesEntities;
    Set<GamesEntity> gamesEntitySet;

    public TestDataBaseAccess() {
        this.userEntitySet = new HashSet<>();
        this.snakesEntities = new HashSet<>();
        this.gamesEntitySet = new HashSet<>();


        int id = 0;

        UserEntity j = new TestUserEntity(++id, "bobodegauche", "bobodegauche@melanchon2017.fr", "julou", "melanchon");
        UserEntity v = new TestUserEntity(++id, "gpasdidee", "valentine@valentine.fr", "valou", "valou");
        UserEntity p = new TestUserEntity(++id, "gauchiassededroite", "paulin@paulin.fr", "paulinou", "hirand");

        userEntitySet.add(j);
        userEntitySet.add(v);
        userEntitySet.add(p);

        id = 0;
        SnakeEntity sj = new TestSnakeEntity(j, ++id, "serpentin", 111, "Some info".getBytes());
        snakesEntities.add(sj);
        SnakeEntity sj2 = new TestSnakeEntity(j, ++id, "serpentin2", 111, "Some info".getBytes());
        snakesEntities.add(sj2);
        SnakeEntity sv = new TestSnakeEntity(v, ++id, "grosnez", 111, "Some info".getBytes());
        snakesEntities.add(sv);
        SnakeEntity sp = new TestSnakeEntity(p, ++id, "ptitzizi", 111, "Some info".getBytes());
        snakesEntities.add(sp);
        SnakeEntity sp2 = new TestSnakeEntity(p, ++id, "groszizi", 111, "Some info".getBytes());
        snakesEntities.add(sp2);

        id = 0;

        GamesEntity g1 = new TestGameEntity(++id, TestGameModeEntity.SINGLE_PLAYER, new Timestamp(2018, 01, 23, 14, 13, 0, 0), new Timestamp(2018, 01, 23, 14, 15, 23, 0));
        g1.getGameParticipations().add(new TestGameParticipationEntity(sp, g1, 400, 1, 0));
        GamesEntity g2 = new TestGameEntity(++id, TestGameModeEntity.CLASSIC_DM, new Timestamp(1561416), new Timestamp(1861516));
        g2.getGameParticipations().add(new TestGameParticipationEntity(sp, g2, 556, 0, 1));
        g2.getGameParticipations().add(new TestGameParticipationEntity(sv, g2, 482, 1, 1));
        g2.getGameParticipations().add(new TestGameParticipationEntity(sj2, g2, 185, 1, 0));

        for (UserEntity u : userEntitySet) {
            System.out.println(u);
        }

    }

    @Override
    public UserEntity getUser(int id) {
        Optional<UserEntity> opt = userEntitySet.parallelStream().filter(userEntity -> userEntity.getId() == id).findFirst();
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }

    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias) {
        return userEntitySet.stream()
                .filter(userEntity -> userEntity.getAlias().equals(alias))
                .collect(Collectors.toList());
    }

    @Override
    public SnakeEntity getSnakeById(int id) {
        Optional<SnakeEntity> opt = snakesEntities.stream().filter(snakesEntity -> snakesEntity.getId() == id).findFirst();
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(UserEntity userEntity) {
        return userEntity.getSnakes();
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        UserEntity user = this.getUser(userId);
        if (user != null) {
            return user.getSnakes();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        UserEntity user = this.getUser(userId);
        if (user != null) {
            return this.getGameResultsByUser(user, sortBy, earliest, latest);
        }
        return null; //TODO exception UserNotFoundException
    }

    private List<GameParticipationEntity> sortGameParticipationEntity(List<GameParticipationEntity> list, int sortBy) {
        switch (sortBy) {
            case SORT_BY_EARLIEST_DATE:
                list.sort((gameParticipationEntity, t1) -> gameParticipationEntity.getGame().getStartTime().compareTo(t1.getGame().getStartTime()));
                break;
            case SORT_BY_LATEST_DATE:
                list.sort((gameParticipationEntity, t1) -> -gameParticipationEntity.getGame().getStartTime().compareTo(t1.getGame().getStartTime()));
                break;
            case SORT_BY_SCORE_ASC:
                list.sort(Comparator.comparingInt(GameParticipationEntity::getScore));
                break;
            case SORT_BY_SCORE_DESC:
                list.sort(Comparator.comparingInt(GameParticipationEntity::getScore).reversed());
                break;
        }

        return list;
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(UserEntity userEntity, int sortBy, Timestamp earliest, Timestamp latest) {

        List<GameParticipationEntity> resultList = new ArrayList<>();

        Predicate<GameParticipationEntity> timestampPredicate;

        if (earliest != null) {
            if (latest != null) {
                //Date min et date max
                timestampPredicate = participation ->
                        participation.getGame().getEndTime().compareTo(latest) <= 0
                                && participation.getGame().getEndTime().compareTo(earliest) >= 0;
            } else {
                //Seulement une date min
                timestampPredicate = participation -> participation.getGame().getEndTime().compareTo(earliest) >= 0;
            }
        } else if (latest != null) {
            //Seulement une date max
            timestampPredicate = participation -> participation.getGame().getEndTime().compareTo(latest) <= 0;
        } else {
            //Pas de date min ni de date max -> toujours vrai
            timestampPredicate = participation -> true;
        }

        //Pour tous les snakes appartenant à l'utilisateur donné
        for (SnakeEntity s : userEntity.getSnakes()) {
            //On filtre les parties correspondantes aux dates données, et on les ajoutes à la liste résultat
            resultList.addAll(
                    s.getGameParticipations().parallelStream()
                            .filter(timestampPredicate)
                            .collect(Collectors.toList()));
        }

        //Tri de la liste en fonction de l'option de tri choisi


        return sortGameParticipationEntity(resultList, sortBy);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameid, int sortBy) {
        GamesEntity gamesEntity = this.getGame(gameid);
        if (gamesEntity == null) {
            return null; //TODO exception NoGameFound
        }

        List<GameParticipationEntity> resultList = new ArrayList<>(gamesEntity.getGameParticipations());

        return sortGameParticipationEntity(resultList, sortBy);
    }

    @Override
    public Collection<GameParticipationEntity> getGameResultsByGame(GamesEntity gamesEntity) {
        return gamesEntity.getGameParticipations();
    }

    @Override
    public GamesEntity getGame(int id) {
        Optional<GamesEntity> opt = gamesEntitySet.stream().filter(gamesEntity -> gamesEntity.getId() == id).findFirst();
        if (opt.isPresent()) {
            return opt.get();
        }
        return null; //TODO Exception NoGameFound
    }

    private List<GamesEntity> sortGames(List<GamesEntity> gamesEntityList, int sortBy) {
        switch (sortBy) {
            case SORT_BY_EARLIEST_DATE:
                gamesEntityList.sort(Comparator.comparing(GamesEntity::getEndTime));
                break;
            case SORT_BY_LATEST_DATE:
                gamesEntityList.sort(Comparator.comparing(GamesEntity::getEndTime).reversed());
                break;
        } //TODO MAY BE : sort by the best score in the game
        return gamesEntityList;
    }

    @Override
    public List<GamesEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy) {

        Predicate<GamesEntity> timestampPredicate;

        if (earliest != null) {
            if (latest != null) {
                //Date min et date max
                timestampPredicate = game ->
                        game.getEndTime().compareTo(latest) <= 0
                                && game.getEndTime().compareTo(earliest) >= 0;
            } else {
                //Seulement une date min
                timestampPredicate = game -> game.getEndTime().compareTo(earliest) >= 0;
            }
        } else if (latest != null) {
            //Seulement une date max
            timestampPredicate = game -> game.getEndTime().compareTo(latest) <= 0;
        } else {
            //Pas de date min ni de date max -> toujours vrai
            timestampPredicate = participation -> true;
        }

        List<GamesEntity> resultList = gamesEntitySet.parallelStream().filter(timestampPredicate).collect(Collectors.toList());

        return this.sortGames(resultList, sortBy);
    }

    @Override
    public List<GamesEntity> getGameByGamemode(GameModeEntity gameModeEntity, int sortBy) {
        List<GamesEntity> resultList = gamesEntitySet.parallelStream().
                filter(gamesEntity -> gamesEntity.getGameMode().equals(gameModeEntity)).
                collect(Collectors.toList());

        return sortGames(resultList, sortBy);
    }

    @Override
    public GameModeEntity getGameMode(int id) {

        for (TestGameModeEntity gm : TestGameModeEntity.values()) {
            if (gm.getId() == id) {
                return gm;
            }
        }
        return null;
    }

    @Override
    public GameModeEntity getGameMode(String name) {
        for (TestGameModeEntity gm : TestGameModeEntity.values()) {
            if (gm.getName().equals(name)) {
                return gm;
            }
        }
        return null;
    }

    @Override
    public Collection<GameModeEntity> getAllGameModes() {
        return Arrays.asList(TestGameModeEntity.values());
    }

}
