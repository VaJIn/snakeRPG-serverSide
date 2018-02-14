package fr.vajin.snakerpg;


import fr.vajin.snakerpg.beans.PlayerBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LeaderBoardServlet")
public class LeaderBoardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<PlayerBean> listPlayer = new ArrayList<>();

        listPlayer.add(new PlayerBean(2, "Tata", 400, LocalDateTime.of(2018, 01, 23, 14, 13)));
        listPlayer.add(new PlayerBean(0, "Toto", 150, LocalDateTime.now()));
        listPlayer.add(new PlayerBean(1, "Titi", 100, LocalDateTime.of(2017, 05, 12, 10, 30)));

        request.setAttribute("leaderboard_playerlist", listPlayer);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/leaderboardView.jsp").include(request, response);
    }
}
