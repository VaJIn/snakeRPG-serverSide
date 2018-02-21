package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.UserEntity;

import java.util.Collection;
import java.util.Optional;

public interface UserDAO {

    void addUser(UserEntity userEntity);

    Optional<UserEntity> getUser(int id);

    Optional<UserEntity> getUser(String accountName, String hash);

    Collection<UserEntity> getUserByAlias(String alias);

    Optional<UserEntity> getUserByAccountName(String accountName);
}
