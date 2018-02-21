import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class GameDAOTest {

    GameDAO gameDAO = DAOFactoryProvider.getDAOFactory().getGameDAO();

    @Test
    @DisplayName("Test GameDAO getGame(int id)")
    void testGetGame(){

        Assertions.assertFalse(gameDAO.getGame(-1).isPresent());

        Optional<GameEntity> optionalGame1 = gameDAO.getGame(1);

        Assertions.assertTrue(optionalGame1.isPresent());
        GameEntity game1 = optionalGame1.get();
        Assertions.assertEquals(1, game1.getId());

        Assertions.assertNotNull(game1.getGameMode());
        GameModeEntity gameModeGame1 = game1.getGameMode();
        Assertions.assertEquals(1, gameModeGame1.getId());

        Assertions.assertNotNull(game1.getParticipationEntitySet());
        Assertions.assertEquals(1, game1.getParticipationEntitySet().size());

        Assertions.assertNotNull(game1.getStartTime());

        //Pb de timezone TODO
        //Assertions.assertEquals(new Timestamp(118,01,21,12,11,10,0), game1.getStartTime());

        Assertions.assertNotNull(game1.getEndTime());
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
        Assertions.assertTrue(games.isEmpty());

        GameModeEntity singlePlayer = DAOFactoryProvider.getDAOFactory().getGameModeDAO().getGameMode(1).get();

        games = gameDAO.getGameByGameMode(singlePlayer, DAOFactory.SORT_BY_EARLIEST_DATE);
        Assertions.assertEquals(1, games.size());

    }
}
