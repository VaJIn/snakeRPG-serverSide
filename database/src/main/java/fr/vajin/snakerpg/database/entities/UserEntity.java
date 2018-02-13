package fr.vajin.snakerpg.database.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class UserEntity {

    private int id;
    private String email;
    private String accountName;
    private String alias;
    private String password;
    private Collection<SnakeEntity> snakes;

    public UserEntity() {
        id = -1;
        email = "";
        accountName = "";
        alias = "";
        password = "";
        snakes = new HashSet<>();
    }

    public UserEntity(int id, String email, String accountName, String alias, String password, Collection<SnakeEntity> snakes) {
        this.id = id;
        this.email = email;
        this.accountName = accountName;
        this.alias = alias;
        this.password = password;
        this.snakes = snakes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<SnakeEntity> getSnakes() {
        return snakes;
    }

    public void setSnakes(Collection<SnakeEntity> snakes) {
        this.snakes = snakes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(email, that.email) &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, accountName, alias, password);
    }
}
