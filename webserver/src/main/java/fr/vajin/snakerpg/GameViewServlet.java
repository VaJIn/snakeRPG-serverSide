package fr.vajin.snakerpg;


import fr.vajin.snakerpg.beans.GameBeans;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GameInfoServlet")
public class GameViewServlet extends HttpServlet {

    DataRetriever dataRetriever = new DateRetrieverDummy();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        int gameId;

        //TODO handle exception
        try {
            gameId = Integer.parseInt(uri.replace("/game/", ""));
        } catch (NumberFormatException e) {
            gameId = -111;
            e.printStackTrace();
        }

        GameBeans game = dataRetriever.getGame(gameId);

        request.setAttribute("game", game);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/gameview.jsp").forward(request, response);
    }

}
