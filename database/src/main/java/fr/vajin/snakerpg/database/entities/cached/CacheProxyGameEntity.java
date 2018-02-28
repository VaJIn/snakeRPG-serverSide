package fr.vajin.snakerpg.database.entities.cached;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

public class CacheProxyGameEntity extends GameEntity {

    transient private DAOFactory daoFactory;
    private boolean retrievedGameParticipation = false;

    public CacheProxyGameEntity(DAOFactory daoFactory) {
        super();
        this.daoFactory = daoFactory;
    }

    public CacheProxyGameEntity(DAOFactory daoFactory, int id, Timestamp startTime, Timestamp endTime, GameModeEntity gameMode) {
        super(id, startTime, endTime, gameMode);
        this.daoFactory = daoFactory;
    }

    @Override
    public Set<GameParticipationEntity> getGameParticipationEntities() {
        if (!retrievedGameParticipation) {
            GameParticipationDAO gameParticipationDAO = daoFactory.getGameParticipationDAO();
            Collection<GameParticipationEntity> gameParticipationEntities = gameParticipationDAO.getGameResultsByGame(this.getId(), DAOFactory.SORT_BY_SCORE_DESC, false);
            this.setGameParticipationEntities(gameParticipationEntities);
        }
        return super.getGameParticipationEntities();
    }

    @Override
    public void setGameParticipationEntities(Collection<GameParticipationEntity> gameParticipationEntities) {
        super.setGameParticipationEntities(gameParticipationEntities);
        this.retrievedGameParticipation = true;
    }
}
