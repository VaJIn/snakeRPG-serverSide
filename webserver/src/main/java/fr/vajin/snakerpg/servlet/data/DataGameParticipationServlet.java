package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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

        Optional<GameParticipationEntity> gameParticipationEntityOptional =
                FactoryProvider
                        .getDAOFactory()
                        .getGameParticipationDAO()
                        .getGameParticipationByIds(idGame, idSnake, 0);

        response.setContentType("application/json");

        if (gameParticipationEntityOptional.isPresent()) {
            Gson gson = new Gson();

            gameParticipationJSON = gson.toJson(gameParticipationEntityOptional.get());

            response.getWriter().write(gameParticipationJSON);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
