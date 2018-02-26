package fr.vajin.snakerpg.database.daoimpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CachedSnakeDAO implements SnakeDAO {


    private SnakeDAO snakeDAO;
    private DAOFactory daoFactory;
    private LoadingCache<Integer, CachedSnake> snakeLoadingCache;
    private LoadingCache<Integer, CachedUserSnakes> userIdSnakeLoadingCache;

    public CachedSnakeDAO(SnakeDAO snakeDAO, DAOFactory daoFactory, int maximumCacheSize, long expireTime, TimeUnit expireTimeUnit) {
        this.snakeDAO = snakeDAO;
        this.daoFactory = daoFactory;

        this.snakeLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(maximumCacheSize)
                .expireAfterWrite(expireTime, expireTimeUnit)
                .build(new CacheLoader<Integer, CachedSnake>() {
                    @Override
                    public CachedSnake load(Integer key) throws Exception {
                        Optional<SnakeEntity> snakeEntity = snakeDAO.getSnakeById(key, false);
                        return new CachedSnake(snakeEntity.orElse(null), false);
                    }
                });
        this.userIdSnakeLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(maximumCacheSize)
                .expireAfterWrite(expireTime, expireTimeUnit)
                .build(new CacheLoader<Integer, CachedUserSnakes>() {
                    @Override
                    public CachedUserSnakes load(Integer key) throws Exception {
                        Collection<SnakeEntity> snakeEntities = snakeDAO.getSnakeByUser(key, false);
                        return new CachedUserSnakes(snakeEntities, false, key);
                    }
                });
    }

    @Override
    public void addSnake(SnakeEntity snakeEntity) throws SQLException {
        snakeDAO.addSnake(snakeEntity);
        userIdSnakeLoadingCache.invalidate(snakeEntity.getUserId());
    }

    @Override
    public Optional<SnakeEntity> getSnakeById(int id) {
        return getSnakeById(id, true);
    }

    @Override
    public Optional<SnakeEntity> getSnakeById(int id, boolean retrieveUserEntity) {
        try {
            CachedSnake cachedSnake = snakeLoadingCache.get(id);
            if (!cachedSnake.retrievedUserEntity) {
                cachedSnake.retrieveUserEntity();
            }
            return cachedSnake.getSnakeEntity();
        } catch (ExecutionException e) {
            //Should never happen
        }
        return Optional.empty();
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        return getSnakeByUser(userId, true);
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId, boolean retrieveUserEntity) {
        try {
            CachedUserSnakes cachedUserSnakes = this.userIdSnakeLoadingCache.get(userId);
            if (retrieveUserEntity) {
                if (!cachedUserSnakes.retrievedUser) {
                    cachedUserSnakes.retrieveUserEntity();
                }
            }
            return cachedUserSnakes.snakeEntities;
        } catch (ExecutionException e) {
        }
        return Collections.emptySet();
    }

    private class CachedSnake {
        boolean retrievedUserEntity;
        private SnakeEntity snakeEntity;

        private CachedSnake(SnakeEntity snakeEntity, boolean retrievedUserEntity) {
            this.snakeEntity = snakeEntity;
            this.retrievedUserEntity = retrievedUserEntity;
        }

        Optional<SnakeEntity> getSnakeEntity() {
            return Optional.ofNullable(this.snakeEntity);
        }

        void retrieveUserEntity() {
            if (!this.retrievedUserEntity && this.snakeEntity != null) {
                UserDAO userDAO = daoFactory.getUserDAO();
                Optional<UserEntity> userEntityOptional = userDAO.getUser(snakeEntity.getUserId(), false);
                userEntityOptional.ifPresent(userEntity -> snakeEntity.setUser(userEntity));
                this.retrievedUserEntity = true;
            }
        }
    }

    private class CachedUserSnakes {
        final int userId;
        boolean retrievedUser;
        private Collection<SnakeEntity> snakeEntities;

        private CachedUserSnakes(Collection<SnakeEntity> snakeEntities, boolean retrievedUser, int userId) {
            this.snakeEntities = snakeEntities;
            this.retrievedUser = retrievedUser;
            this.userId = userId;
        }

        private Collection<SnakeEntity> getSnakeEntities() {
            return snakeEntities;
        }

        private void retrieveUserEntity() {
            if (!this.retrievedUser && !snakeEntities.isEmpty()) {
                UserDAO userDAO = daoFactory.getUserDAO();
                Optional<UserEntity> userEntityOptional = userDAO.getUser(userId, false);
                userEntityOptional.ifPresent(userEntity -> {
                    for (SnakeEntity s : snakeEntities) {
                        s.setUser(userEntity);
                    }
                });
                this.retrievedUser = true;
            }
        }
    }
}
