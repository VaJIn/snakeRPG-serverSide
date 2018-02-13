package fr.vajin.snakerpg.database.test;

import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TestUserEntity implements UserEntity {

    private int id;
    private String accountName;
    private String email;
    private String alias;
    private String password;
    private Set<SnakeEntity> snakes;

    public TestUserEntity(int id, String accountName, String email, String alias, String password) {

        this.snakes = new HashSet<>();

        this.id = id;
        this.accountName = accountName;
        this.email = email;
        this.alias = alias;
        this.password = password;
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
    public String getAccountName() {
        return accountName;
    }

    @Override
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Set<SnakeEntity> getSnakes() {
        return snakes;
    }

    public void setSnakes(Collection<SnakeEntity> snakes) {
        this.snakes = new HashSet<>(snakes);
        for (SnakeEntity snakeEntity : snakes) {
            snakeEntity.setUser(this);
        }
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "TestUserEntity{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                ", alias='" + alias + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestUserEntity that = (TestUserEntity) o;

        if (id != that.id) return false;
        if (!accountName.equals(that.accountName)) return false;
        if (!email.equals(that.email)) return false;
        if (!alias.equals(that.alias)) return false;
        if (!password.equals(that.password)) return false;
        return snakes.equals(that.snakes);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + accountName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + alias.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + snakes.hashCode();
        return result;
    }
}
