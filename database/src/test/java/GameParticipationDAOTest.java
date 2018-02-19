import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.daoimpl.GameParticipationDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

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

        Assertions.assertNull(gameParticipationDAO.getGameResultsByGame(-1,0));
        Assertions.assertNotNull(gameParticipationDAO.getGameResultsByGame(1,0));

    }

    @Test
    @DisplayName("Test GameParticipation getGameParticipationByIds")
    void testGetGameResultsByIds(){
        Assertions.assertNull(gameParticipationDAO.getGameParticipationByIds(-1,-1,0));
        Assertions.assertNotNull(gameParticipationDAO.getGameParticipationByIds(1,1,0));
    }
}
