import fr.vajin.snakerpg.database.DataBaseAccess;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.database.test.TestDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

public class TestDatabaseAccessJDBC {

    DataBaseAccess dataBaseAccess = new TestDatabase();

    @Test
    @DisplayName("Test getUserById")
    void testGetUserById(){

        UserEntity user = dataBaseAccess.getUser(-1);
        Assertions.assertNull(user);
    }

    @Test
    @DisplayName("Test getGameResultsByUser")
    void testGetGameResultsByUser(){
        List<GameParticipationEntity> listGPE = dataBaseAccess.getGameResultsByUser(2,0, new Timestamp(0),new Timestamp(0));
        for(GameParticipationEntity gp : listGPE){
            System.out.println(gp);
        }
    }

}
