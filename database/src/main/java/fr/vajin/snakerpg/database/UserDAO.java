package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.UserEntity;

import java.util.Collection;

public interface UserDAO {

    UserEntity getUser(int id);

    UserEntity getUser(String accountName, String hash);

    Collection<UserEntity> getUserByAlias(String alias);

    UserEntity getUserByAccountName(String accountName);
}
