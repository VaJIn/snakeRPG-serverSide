package fr.vajin.snakerpg.database.entities;

import java.util.Collection;

public interface UserEntity {
    int getId();

    void setId(int id);

    String getEmail();

    void setEmail(String email);

    String getAccountName();

    void setAccountName(String accountName);

    String getAlias();

    void setAlias(String alias);

    String getPassword();

    void setPassword(String password);

    Collection<SnakeEntity> getSnakes();

    void setSnakes(Collection<SnakeEntity> snakes);
}
