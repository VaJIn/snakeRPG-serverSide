package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.UserEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DataUserServlet extends HttpServlet {

    static final String USER_ID_PARAMETER = "userId";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id;
        try {
            id = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<UserEntity> userEntityOptional = FactoryProvider.getDAOFactory().getUserDAO().getUser(id);

        if (userEntityOptional.isPresent()) {
            Gson gson = new Gson();

            String userJSON = gson.toJson(userEntityOptional.get());

            response.setContentType("application/json");
            response.getWriter().write(userJSON);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }


    }

}
