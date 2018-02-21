import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.daoimpl.GameDAOImpl;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

public class GameDAOTest {

    GameDAO gameDAO = new GameDAOImpl();

    @Test
    @DisplayName("Test GameDAO getGame(int id)")
    void testGetGame(){

        Assertions.assertFalse(gameDAO.getGame(-1).isPresent());
        Assertions.assertTrue(gameDAO.getGame(1).isPresent());
    }

    @Test
    @DisplayName("Test GameDAO getGameByDate")
    void testGetGameByDate(){

        Collection<GameEntity> games = gameDAO.getGameByDate(null, null, DAOFactory.SORT_BY_EARLIEST_DATE);
        Assertions.assertNotNull(games);
        Assertions.assertEquals(3, games.size()); //3 games in test database

        GameEntity act, pred;
        Iterator<GameEntity> it = games.iterator();
        act = it.next();
        while (it.hasNext()) {
            pred = act;
            act = it.next();
            Assertions.assertTrue(pred.getStartTime().compareTo(act.getStartTime()) <= 0);
        }
    }

    @Test
    @DisplayName("Test GameDAO getGameByMode")
    void testGetGameByGameMode(){
        Collection<GameEntity> games = gameDAO.getGameByGameMode(new GameModeEntity(),0);
        Assertions.assertNotNull(games);
        Assertions.assertEquals(0,games.size());
    }
}
