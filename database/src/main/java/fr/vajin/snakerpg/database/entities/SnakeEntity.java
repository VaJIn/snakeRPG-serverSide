package fr.vajin.snakerpg.database.entities;

import java.util.Collection;

public interface SnakeEntity {
    int getUserId();

    void setUserId(int userId);

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    int getExpPoint();

    void setExpPoint(int expPoint);

    byte[] getInfo();

    void setInfo(byte[] info);

    Collection<GameParticipationEntity> getGameParticipations();

    void setGameParticipations(Collection<GameParticipationEntity> gameParticipations);

    UserEntity getUser();

    void setUser(UserEntity userByUserId);
}
