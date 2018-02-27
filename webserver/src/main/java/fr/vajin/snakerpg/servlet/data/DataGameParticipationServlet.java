package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DataGameParticipationServlet extends HttpServlet {
    static final String GAME_ID_PARAMETER ="gameId";
    static final String SNAKE_ID_PARAMETER = "snakeId";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idGame, idSnake;
        try {
            idGame = Integer.parseInt(request.getParameter(GAME_ID_PARAMETER));
            idSnake = Integer.parseInt(request.getParameter(SNAKE_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<GameParticipationEntity> gameParticipation = FactoryProvider.getDAOFactory().getGameParticipationDAO().getGameParticipationByIds(idGame,idSnake,0);

        Gson gson = new Gson();

        String gameParticipationJSON = gson.toJson(gameParticipation);

        response.setContentType("application/json");
        response.getWriter().write(gameParticipationJSON);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
