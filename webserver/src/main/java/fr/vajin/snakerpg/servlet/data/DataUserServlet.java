package fr.vajin.snakerpg.servlet.data;

import fr.vajin.snakerpg.DAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DataUserServlet extends HttpServlet {

    static final String USER_ID_PARAMETER = "userId";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id;
        try {
            id = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserEntity user = DAO.getInstance().getAccessor().getUser(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        JSONObject object = new JSONObject();

        object.put("id", user.getId());
        object.put("alias", user.getAlias());
        JSONArray snakesJSON = new JSONArray();
        for (SnakeEntity entity : user.getSnakes()) {
            JSONObject snake = new JSONObject();
            snake.put("id", entity.getId());
            snake.put("name", entity.getName());
            snake.put("exp", entity.getExpPoint());
            snake.put("info", entity.getInfo());
            snakesJSON.add(snake);
        }
        object.put("snakes", snakesJSON);

        response.setContentType("application/json");
        response.getWriter().write(object.toJSONString());
    }

}
