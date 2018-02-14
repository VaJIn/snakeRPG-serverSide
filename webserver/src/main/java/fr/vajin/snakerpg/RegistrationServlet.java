package fr.vajin.snakerpg;

import com.sun.org.apache.regexp.internal.RE;
import fr.vajin.snakerpg.form.RegistrationFormLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {

    final static String VIEW = "/WEB-INF/registrationPage.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("user", "lol");
        request.setAttribute("connected", true);

        Map<String, String> idMap = new HashMap<>();
        idMap.put("formName", RegistrationFormLogic.FORM_NAME);
        idMap.put("email", RegistrationFormLogic.CHAMP_EMAIL);
        idMap.put("alias", RegistrationFormLogic.CHAMP_ALIAS);
        idMap.put("accountName", RegistrationFormLogic.CHAMP_ACCOUNT_NAME);
        idMap.put("password", RegistrationFormLogic.CHAMP_PASS);
        idMap.put("confirmation", RegistrationFormLogic.CHAMP_CONF);

        request.setAttribute("ids", idMap);

        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);

    }
}
