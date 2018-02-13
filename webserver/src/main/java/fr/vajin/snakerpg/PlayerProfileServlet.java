package fr.vajin.snakerpg;

import fr.vajin.snakerpg.database.entities.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlayerProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userIdStr = request.getParameter("id");

        UserEntity user;

        int userId;

        if (userIdStr == null) {
            userId = 1;
        } else {
            try {
                userId = Integer.parseInt(userIdStr);
            } catch (NumberFormatException e) {
                userId = 1;
            }
        }

        user = DAO.getInstance().getAccessor().getUser(userId);

        request.setAttribute("user", user);

        this.getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }
}
