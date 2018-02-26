package fr.vajin.snakerpg.database.daoimpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameModeDAO;
import fr.vajin.snakerpg.database.entities.GameModeEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CachedGameModeDAO implements GameModeDAO {

    public static int NO_GAMEMODE = -1;

    private DAOFactory daoFactory;

    private GameModeDAO gameModeDAO;

    private LoadingCache<Integer, Optional<GameModeEntity>> loadingCacheById;
    private LoadingCache<String, Integer> loadingCacheNameId;

    public CachedGameModeDAO(GameModeDAO gameModeDAO, DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.gameModeDAO = gameModeDAO;

        this.loadingCacheById = CacheBuilder.newBuilder().maximumSize(100).build(new CacheLoader<Integer, Optional<GameModeEntity>>() {
            @Override
            public Optional<GameModeEntity> load(Integer key) throws Exception {
                return gameModeDAO.getGameMode(key);
            }
        });

        this.loadingCacheNameId = CacheBuilder.newBuilder().maximumSize(100).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                Optional<GameModeEntity> optional = gameModeDAO.getGameMode(key);
                if (optional.isPresent()) {
                    GameModeEntity entity = optional.get();
                    loadingCacheById.put(entity.getId(), optional);
                    return entity.getId();
                } else {
                    return NO_GAMEMODE;
                }
            }
        });
    }

    @Override
    public Collection<GameModeEntity> getAllGameMode() {
        Collection<GameModeEntity> gameModeEntities = gameModeDAO.getAllGameMode();
        for (GameModeEntity gameModeEntity : gameModeEntities) {
            loadingCacheById.put(gameModeEntity.getId(), Optional.of(gameModeEntity));
            loadingCacheNameId.put(gameModeEntity.getName(), gameModeEntity.getId());
        }
        return gameModeEntities;
    }

    @Override
    public Optional<GameModeEntity> getGameMode(int id) {
        try {
            return loadingCacheById.get(id);
        } catch (ExecutionException e) {
        }
        return Optional.empty();
    }

    @Override
    public Optional<GameModeEntity> getGameMode(String name) {
        try {
            int id = loadingCacheNameId.get(name);
            if (id == NO_GAMEMODE) {
                return Optional.empty();
            }
            return loadingCacheById.get(id);
        } catch (ExecutionException e) {
        }
        return Optional.empty();
    }
}
