import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.servlet.data.DataGameServlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

public class DataGameServletTest {
    DataGameServlet gameServlet = new DataGameServlet();

    @Test
    @DisplayName("gameId ok")
    void doPostTest(){

        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("gameId","1");
        HttpServletResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> gameServlet.doPost(request,response));

        Assertions.assertEquals("application/json",response.getContentType());


    }

    @Test
    @DisplayName("gameId non ok")
    void doPostTestFail(){
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("gameId","100");
        HttpServletResponse response = new MockHttpResponse();

        Assertions.assertThrows(NoSuchElementException.class, ()-> gameServlet.doPost(request,response));

    }

    @Test
    @DisplayName("test JSONString")
    void testJSONString(){
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("gameId","1");
        HttpServletResponse response = new MockHttpResponse();

        Assertions.assertAll(() ->gameServlet.doPost(request,response));

        Gson gson =new Gson();
        String testGameJSON = gson.toJson(FactoryProvider.getDAOFactory().getGameDAO().getGame(1).get());

        Assertions.assertEquals(testGameJSON,gameServlet.getGameJSON());


    }
}