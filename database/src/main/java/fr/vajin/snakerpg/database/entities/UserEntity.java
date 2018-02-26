package fr.vajin.snakerpg.database.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserEntity {

    private int id;
    private String email;
    private String accountName;
    private String alias;
    private String password;
    private Set<SnakeEntity> snakes;

    public UserEntity() {
        id = -1;
        email = "";
        accountName = "";
        alias = "";
        password = "";
        snakes = new HashSet<>();
    }

    public UserEntity(int id, String alias, String email, String accountName, String password) {
        this.id = id;
        this.email = email;
        this.accountName = accountName;
        this.alias = alias;
        this.password = password;
        this.snakes = new HashSet<>();
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

    public Set<SnakeEntity> getSnakes() {
        return snakes;
    }

    public void setSnakes(Collection<SnakeEntity> snakes) {
        this.snakes = new HashSet<>(snakes);
        for (SnakeEntity snake : this.snakes) {
            if (snake.getUser() != this) {
                snake.setUser(this);
            }
        }
    }

    public void addSnake(SnakeEntity snakeEntity) {
        if (snakeEntity.getUser() != this) {
            snakeEntity.setUser(this);
        }
        this.snakes.add(snakeEntity);
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
