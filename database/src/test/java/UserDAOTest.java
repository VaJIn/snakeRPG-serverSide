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
        Assertions.assertNull(userDAO.getUser(-1));
        Assertions.assertNotNull(userDAO.getUser(1));
    }

    @Test
    @DisplayName("Test UserDAO getUser(String accountName,String hash)")
    void testGetUserByAccountNameAndHash(){
        Assertions.assertNull(userDAO.getUser("",""));

        //TODO modifier le password en hash
        Assertions.assertNotNull(userDAO.getUser("mistermuscu","ABABABAB"));
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
        Assertions.assertNull(userDAO.getUserByAccountName(""));
        Assertions.assertNotNull(userDAO.getUserByAccountName("mistermuscu"));
    }
}
