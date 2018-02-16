package fr.vajin.snakerpg.servlet;

import fr.vajin.snakerpg.database.DataBaseAccess;
import fr.vajin.snakerpg.database.DummyDataBaseAccess;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.form.LoginFormLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String VIEW = "/WEB-INF/loginPage.jsp";
    DataBaseAccess dataBaseAccess = new DummyDataBaseAccess();
    private Map<String, String> idsMap;

    public LoginServlet() {
        this.idsMap = new HashMap<>();
        idsMap.put("accountName", LoginFormLogic.CHAMP_ACCOUNT_NAME);
        idsMap.put("password", LoginFormLogic.CHAMP_PASSWORD);
        idsMap.put("form", LoginFormLogic.FORM_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("id", idsMap);

        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LoginFormLogic formLogic = new LoginFormLogic(dataBaseAccess);

        UserEntity userEntity = formLogic.logInUser(request);
        if (userEntity != null)
            response.getWriter().write(userEntity.toString());
        else {
            response.getWriter().println("Null user");
            if (formLogic.getErrors().isEmpty()) {
                response.getWriter().println("No error");
            } else {
                for (Map.Entry<String, String> error : formLogic.getErrors().entrySet()) {
                    response.getWriter().println(error.getKey() + " > " + error.getValue());
                }
            }
        }
        if (userEntity != null) {
            HttpSession session = request.getSession();

            session.setAttribute("user", userEntity);

            response.sendRedirect("/home");
        } else {
            if (formLogic.getErrors().isEmpty())
                formLogic.getErrors().put("login", "Wrong account name or password");
            request.setAttribute("error", formLogic.getErrors());
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
