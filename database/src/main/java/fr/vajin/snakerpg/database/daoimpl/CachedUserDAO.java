package fr.vajin.snakerpg.database.daoimpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Decorator for a UserDAO. Add two caches, one based one user id and one based on account name.
 */
public class CachedUserDAO implements UserDAO {

    private static final int NO_USER = -1;
    private UserDAO userDAO;
    private DAOFactory daoFactory;
    private LoadingCache<Integer, CachedUser> cacheUserById;
    private LoadingCache<String, Integer> cacheUserIdByAccountName;

    public CachedUserDAO(UserDAO userDAO, DAOFactory daoFactory, int maxSize, long expireTime, TimeUnit expireTimeUnit) {
        this.userDAO = userDAO;
        this.daoFactory = daoFactory;

        this.cacheUserById = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireTime, expireTimeUnit)
                .build(new CacheLoader<Integer, CachedUser>() {
                    @Override
                    public CachedUser load(Integer key) throws Exception {
                        Optional<UserEntity> userEntityOptional = userDAO.getUser(key, false);
                        return new CachedUser(userEntityOptional, false);
                    }
                });

        this.cacheUserIdByAccountName = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireTime, expireTimeUnit)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        Optional<UserEntity> userEntityOptional = userDAO.getUserByAccountName(key, false);
                        if (userEntityOptional.isPresent()) {
                            UserEntity user = userEntityOptional.get();
                            if (cacheUserById.getIfPresent(user.getId()) == null) {
                                cacheUserById.put(user.getId(), new CachedUser(userEntityOptional, false));
                            }
                            return user.getId();
                        }
                        return NO_USER;
                    }
                });
    }

    @Override
    public void addUser(UserEntity userEntity) throws SQLException {
        userDAO.addUser(userEntity);
        //   this.cacheUserById.put(userEntity.getId(), new CachedUser(Optional.of(userEntity), false));
    }

    @Override
    public Optional<UserEntity> getUser(int id) {
        return this.getUser(id, true);
    }

    @Override
    public Optional<UserEntity> getUser(int id, boolean retrieveSnake) {
        try {
            CachedUser cachedUser = cacheUserById.get(id);
            if (retrieveSnake) {
                if (!cachedUser.retrievedSnake) {
                    cachedUser.retrieveSnake();
                }
                return cachedUser.getUserEntity();
            } else {
                return cachedUser.getUserEntity();
            }
        } catch (ExecutionException e) {
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> getUser(String accountName, String hash) {
        return this.getUser(accountName, hash, true);
    }

    @Override
    public Optional<UserEntity> getUser(String accountName, String hash, boolean retrieveSnake) {
        Optional<UserEntity> userEntityOptional = getUserByAccountName(accountName, retrieveSnake);
        if (userEntityOptional.isPresent()) {
            UserEntity entity = userEntityOptional.get();
            if (entity.getPassword().equals(hash)) {
                return userEntityOptional;
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias) {
        return this.getUserByAlias(alias, true);
    }

    @Override
    public synchronized Collection<UserEntity> getUserByAlias(String alias, boolean retrieveSnake) {
        Collection<UserEntity> results = userDAO.getUserByAlias(alias, retrieveSnake);

        if (retrieveSnake) {
            //We retrieved the snakes : we put everything in the cache
            for (UserEntity userEntity : results) {
                cacheUserById.put(userEntity.getId(), new CachedUser(Optional.of(userEntity), true));
            }
        } else {
            //We retrieved the snake : we put in the cache only if there is not an earl
            for (UserEntity userEntity : results) {
                if (cacheUserById.getIfPresent(userEntity.getId()) == null) {
                    cacheUserById.put(userEntity.getId(), new CachedUser(Optional.of(userEntity), false));
                }
            }
        }
        return results;
    }

    @Override
    public synchronized Optional<UserEntity> getUserByAccountName(String accountName) {
        return getUserByAccountName(accountName, true);
    }

    @Override
    public Optional<UserEntity> getUserByAccountName(String accountName, boolean retrieveSnake) {
        try {
            int id = cacheUserIdByAccountName.get(accountName);
            if (id == NO_USER) {
                return Optional.empty();
            } else {
                CachedUser cachedUser = cacheUserById.get(id);
                if (retrieveSnake && !cachedUser.retrievedSnake) {
                    cachedUser.retrieveSnake();
                }
                return cachedUser.getUserEntity();
            }
        } catch (ExecutionException e) {
        }
        return Optional.empty();
    }

    private class CachedUser {
        @Nullable
        UserEntity user;
        boolean retrievedSnake;

        private CachedUser(Optional<UserEntity> userOptional, boolean retrievedSnake) {
            this.user = userOptional.orElse(null);
            this.retrievedSnake = retrievedSnake;
        }

        void retrieveSnake() {
            if (user != null && !retrievedSnake) {
                SnakeDAO snakeDAO = daoFactory.getSnakeDAO();
                Collection<SnakeEntity> snakeEntities = snakeDAO.getSnakeByUser(user.getId(), false);

                user.setSnakes(snakeEntities);
                this.retrievedSnake = true;
            }
        }

        Optional<UserEntity> getUserEntity() {
            return Optional.ofNullable(this.user);
        }
    }
}
