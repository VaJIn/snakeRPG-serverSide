package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UserDAO {

    void addUser(UserEntity userEntity) throws SQLException;

    Optional<UserEntity> getUser(int id);

    Optional<UserEntity> getUser(int id, boolean retrieveSnake);

    Optional<UserEntity> getUser(String accountName, String hash);

    Optional<UserEntity> getUser(String accountName, String hash, boolean retrieveSnake);

    Collection<UserEntity> getUserByAlias(String alias);

    Collection<UserEntity> getUserByAlias(String alias, boolean retrieveSnake);

    Optional<UserEntity> getUserByAccountName(String accountName);

    Optional<UserEntity> getUserByAccountName(String accountName, boolean retrieveSnake);
}
