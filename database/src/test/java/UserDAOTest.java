import fr.vajin.snakerpg.database.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDAOTest {

    UserDAO userDAO;

    @Test
    @DisplayName("Test UserDAO getUser(int id)")
    void testGetUserById(){
        Assertions.assertNull(userDAO.getUser(-1));
    }

    @Test
    @DisplayName("Test UserDAO getUser(String accountName,String hash)")
    void testGetUserByAccountNameAndHash(){

    }

    @Test
    @DisplayName("Test UserDAO getUserByAlias")
    void testGetUserByAlias(){
        Assertions.assertNull(userDAO.getUserByAlias(""));
    }

    @Test
    @DisplayName("Test UserDAO getUserByAccountName")
    void testGetUserByAccountName(){
        Assertions.assertNull(userDAO.getUserByAccountName(""));
    }
}
