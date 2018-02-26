package fr.vajin.snakerpg.servlet;

import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PlayerProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userIdStr = request.getParameter("id");

        Optional<UserEntity> user;

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

        user = FactoryProvider.getDAOFactory().getUserDAO().getUser(userId);

        request.setAttribute("user", user.get());

        this.getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }
}
