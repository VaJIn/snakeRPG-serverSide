package fr.vajin.snakerpg.form;

import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginFormLogic {

    public static final String FORM_NAME = "loginForm";
    public static final String CHAMP_ACCOUNT_NAME = FORM_NAME + "-accountName";
    public static final String CHAMP_PASSWORD = FORM_NAME + "-password";
    public static String ERROR_NO_ACCOUNT_NAME = "Please enter your account name to log in";
    public static String ERROR_NO_PASSWORD = "Please enter your password to log in";
    Map<String, String> errors;
    public LoginFormLogic() {
        this.errors = new HashMap<>();
    }

    public void accountNameValidation(String accountName) throws Exception {
        if (accountName == null || accountName.equals("")) {
            throw new Exception(ERROR_NO_ACCOUNT_NAME);
        }
    }

    public void passwordValidation(String password) throws Exception {
        if (password == null || password.equals("")) {
            throw new Exception(ERROR_NO_PASSWORD);
        }
    }

    public Optional<UserEntity> logInUser(HttpServletRequest request) {
        this.errors = new HashMap<>();

        String accountName = request.getParameter(CHAMP_ACCOUNT_NAME);
        String password = request.getParameter(CHAMP_PASSWORD);

        try {
            accountNameValidation(accountName);
        } catch (Exception e) {
            this.errors.put(CHAMP_ACCOUNT_NAME, e.getMessage());
        }

        try {
            passwordValidation(password);
        } catch (Exception e) {
            this.errors.put(CHAMP_PASSWORD, e.getMessage());
        }

        return FactoryProvider.getDAOFactory().getUserDAO().getUser(accountName, password);
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
