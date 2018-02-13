package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TestSnakeEntity implements SnakeEntity {

    UserEntity userEntity;
    int id;
    String name;
    int expPoint;
    byte[] info;
    Set<GameParticipationEntity> gameParticipations;

    public TestSnakeEntity() {
        id = -1;
        name = "Undefined";
    }

    public TestSnakeEntity(UserEntity userEntity, int id, String name, int expPoint, byte[] info) {
        this.gameParticipations = new HashSet<>();

        this.id = id;
        this.name = name;
        this.expPoint = expPoint;
        this.info = info;

        this.userEntity = userEntity;
        userEntity.getSnakes().add(this);


    }

    @Override
    public int getUserId() {
        return userEntity.getId();
    }

    @Override
    public void setUserId(int userId) {

    }

    @Override
    public UserEntity getUser() {
        return userEntity;
    }

    @Override
    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
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
    public int getExpPoint() {
        return expPoint;
    }

    @Override
    public void setExpPoint(int expPoint) {
        this.expPoint = expPoint;
    }

    @Override
    public byte[] getInfo() {
        return info;
    }

    @Override
    public void setInfo(byte[] info) {
        this.info = info;
    }

    @Override
    public Set<GameParticipationEntity> getGameParticipations() {
        return gameParticipations;
    }

    public void setGameParticipations(Collection<GameParticipationEntity> gameParticipations) {
        this.gameParticipations = new HashSet<>(gameParticipations);
        for (GameParticipationEntity gp : this.gameParticipations) {
            gp.setSnake(this);
        }
    }

    @Override
    public String toString() {
        return "TestSnakeEntity{" +
                "userEntity.id=" + userEntity.getId() +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", expPoint=" + expPoint +
                ", info=" + new String(info) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestSnakeEntity that = (TestSnakeEntity) o;

        if (id != that.id) return false;
        if (expPoint != that.expPoint) return false;
        if (!(userEntity == that.userEntity)) return false;
        if (!name.equals(that.name)) return false;
        if (!Arrays.equals(info, that.info)) return false;
        return gameParticipations.equals(that.gameParticipations);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
