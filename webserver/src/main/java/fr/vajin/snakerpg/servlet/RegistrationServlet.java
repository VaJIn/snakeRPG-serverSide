package fr.vajin.snakerpg.servlet;

import fr.vajin.snakerpg.form.RegistrationFormLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {

    private final static String VIEW = "/WEB-INF/registrationPage.jsp";

    private Map<String, String> idsMap;

    public RegistrationServlet() {
        this.idsMap = new HashMap<>();
        idsMap.put("formName", RegistrationFormLogic.FORM_NAME);
        idsMap.put("email", RegistrationFormLogic.CHAMP_EMAIL);
        idsMap.put("alias", RegistrationFormLogic.CHAMP_ALIAS);
        idsMap.put("accountName", RegistrationFormLogic.CHAMP_ACCOUNT_NAME);
        idsMap.put("password", RegistrationFormLogic.CHAMP_PASS);
        idsMap.put("confirmation", RegistrationFormLogic.CHAMP_CONF);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("ids", idsMap);

        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);

    }
}
