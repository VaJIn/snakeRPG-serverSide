import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.daoimpl.UserDAOImpl;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class UserDAOTest {

    UserDAO userDAO = new UserDAOImpl();

    @Test
    @DisplayName("Test UserDAO getUser(int id)")
    void testGetUserById(){
        Assertions.assertFalse(userDAO.getUser(-1).isPresent());
        Assertions.assertTrue(userDAO.getUser(1).isPresent());
    }

    @Test
    @DisplayName("Test UserDAO getUser(String accountName,String hash)")
    void testGetUserByAccountNameAndHash(){
        Assertions.assertFalse(userDAO.getUser("", "").isPresent());

        //TODO modifier le password en hash
        Assertions.assertTrue(userDAO.getUser("mistermuscu", "ABABABAB").isPresent());
    }

    @Test
    @DisplayName("Test UserDAO getUserByAlias")
    void testGetUserByAlias(){
        Collection<UserEntity> users = userDAO.getUserByAlias("");
        Assertions.assertNotNull(users);
        Assertions.assertEquals(0,users.size());

        Assertions.assertNotNull(userDAO.getUserByAlias("leBGdu72"));
    }

    @Test
    @DisplayName("Test UserDAO getUserByAccountName")
    void testGetUserByAccountName(){
        Assertions.assertFalse(userDAO.getUserByAccountName("").isPresent());
        Assertions.assertTrue(userDAO.getUserByAccountName("mistermuscu").isPresent());
    }
}
