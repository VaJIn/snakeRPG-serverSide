package fr.vajin.snakerpg.servlet;


import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "LeaderBoardServlet")
public class LeaderBoardServlet extends HttpServlet {

    GameParticipationDAO gameParticipationDAO = FactoryProvider.getDAOFactory().getGameParticipationDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Collection<GameParticipationEntity> gameParticipationEntities = gameParticipationDAO.getGameParticipation(null, null, DAOFactory.SORT_BY_SCORE_DESC);

        request.setAttribute("listGameParticipation", gameParticipationEntities);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/leaderboardView.jsp").include(request, response);
    }
}
