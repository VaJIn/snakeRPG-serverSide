import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.daoimpl.DAOFactoryImpl;
import fr.vajin.snakerpg.database.daoimpl.UserDAOImpl;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

public class UserDAOTest {

    DAOFactory factory = new DAOFactoryImpl();
    UserDAO userDAO = factory.getUserDAO();

    @Test
    @DisplayName("Test UserDAO getUser(int id)")
    void testGetUserById(){
        Assertions.assertFalse(userDAO.getUser(-1).isPresent());

        Optional<UserEntity> optionalUser1 = userDAO.getUser(1);

        Assertions.assertTrue(optionalUser1.isPresent());

        UserEntity user1 = optionalUser1.get();

        Assertions.assertEquals(1, user1.getId());
        Assertions.assertEquals("user1", user1.getAccountName());
        Assertions.assertEquals("alias1", user1.getAlias());
        Assertions.assertEquals("user1@domain.fr", user1.getEmail());
    }

    @Test
    @DisplayName("Test UserDAO getUser(String accountName,String hash)")
    void testGetUserByAccountNameAndHash(){
        Assertions.assertFalse(userDAO.getUser("", "").isPresent());

        //TODO modifier le password en hash
        Assertions.assertTrue(userDAO.getUser("user2", "123456").isPresent());
    }

    @Test
    @DisplayName("Test UserDAO getUserByAlias")
    void testGetUserByAlias(){
        Collection<UserEntity> users = userDAO.getUserByAlias("");
        Assertions.assertNotNull(users);
        Assertions.assertEquals(0,users.size());

        Assertions.assertNotNull(userDAO.getUserByAlias("alias3"));
    }

    @Test
    @DisplayName("Test UserDAO getUserByAccountName")
    void testGetUserByAccountName(){
        Assertions.assertFalse(userDAO.getUserByAccountName("").isPresent());

        Assertions.assertTrue(userDAO.getUserByAccountName("user2").isPresent());
    }
}