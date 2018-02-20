import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.daoimpl.GameParticipationDAOImpl;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collection;

public class GameParticipationDAOTest {

    GameParticipationDAO gameParticipationDAO = new GameParticipationDAOImpl();

    @Test
    @DisplayName("Test GameParticipationDAO getGameResultsByUser")
    void testGetGameResultsByUser(){

        Assertions.assertNotNull(gameParticipationDAO.getGameResultsByUser(1,0,new Timestamp(0),new Timestamp(0)));
    }

    @Test
    @DisplayName("Test GameParticipationDAO getGameResultsByGame")
    void testGetGameResultsByGame(){

        Collection<GameParticipationEntity> results = gameParticipationDAO.getGameResultsByGame(-1,0);
        Assertions.assertNotNull(results);
        Assertions.assertEquals(0,results.size());
        Assertions.assertNotNull(gameParticipationDAO.getGameResultsByGame(1,0));

    }

    @Test
    @DisplayName("Test GameParticipation getGameParticipationByIds")
    void testGetGameResultsByIds(){
        Collection<GameParticipationEntity> gp = gameParticipationDAO.getGameParticipationByIds(-1,-1,0);
        Assertions.assertNotNull(gp);
        Assertions.assertEquals(0,gp.size());
        Assertions.assertNotNull(gameParticipationDAO.getGameParticipationByIds(1,1,0));
    }
}
