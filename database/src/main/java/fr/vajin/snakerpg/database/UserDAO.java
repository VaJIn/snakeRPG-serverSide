package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UserDAO {

    void addUser(UserEntity userEntity) throws SQLException;

    Optional<UserEntity> getUser(int id);

    Optional<UserEntity> getUser(String accountName, String hash);

    Collection<UserEntity> getUserByAlias(String alias);

    Optional<UserEntity> getUserByAccountName(String accountName);
}
