package fr.vajin.snakerpg.form;

import fr.vajin.snakerpg.database.entities.UserEntity;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServletRequest;

public class RegistrationFormLogic {

    public static final String FORM_NAME = "registerForm";

    public static final String CHAMP_EMAIL = "[" + FORM_NAME + "]email";

    public static final String CHAMP_PASS = "[" + FORM_NAME + "]password";

    public static final String CHAMP_CONF = "[" + FORM_NAME + "]confirmation";

    public static final String CHAMP_ALIAS = "[" + FORM_NAME + "]alias";

    public static final String CHAMP_ACCOUNT_NAME = "[" + FORM_NAME + "]accountName";

    public static final int MIN_LENGTH_ALIAS = 6;
    public static final int MAX_LENGTH_ALIAS = 30;

    public static final int MIN_LENGTH_ACCOUNT_NAME = 6;
    public static final int MAX_LENGTH_ACCOUNT_NAME = 30;

    public static final int MIN_PASSWORD_LENGTH = 6;
    public final static String NULL_EMAIL_ERROR_MSG = "Please enter your email adress";
    public final static String INVALID_EMAIL_ERROR_MSG = "Please enter a valid email address.";

    public UserEntity registerUser(HttpServletRequest request) {

        UserEntity userEntity = new UserEntity();

        return userEntity;
    }

    public void emailValidation(String email) throws Exception {
        if (email == null) {
            throw new Exception(NULL_EMAIL_ERROR_MSG);
        }
        boolean valid = EmailValidator.getInstance(true).isValid(email);
        if (!valid) {
            throw new Exception(INVALID_EMAIL_ERROR_MSG);
        }
    }

    public void aliasValidation(String alias) throws Exception {
        if (alias == null) {
            throw new Exception("Alias can not be empty");
        }
        alias = alias.trim();
        if (alias.length() < MIN_LENGTH_ALIAS || alias.length() > MAX_LENGTH_ALIAS) {
            throw new Exception("Alias must have between " + MIN_LENGTH_ALIAS + " and " + MAX_LENGTH_ALIAS + " characters.");
        }
    }

    public void accountNameValidation(String accountName) throws Exception {
        if (accountName == null) {
            throw new Exception("Account name can not be empty");
        }
        accountName = accountName.trim();
        if (accountName.length() < MIN_LENGTH_ACCOUNT_NAME || accountName.length() > MAX_LENGTH_ACCOUNT_NAME) {
            throw new Exception("Account name must have between " + MIN_LENGTH_ACCOUNT_NAME + " and " + MAX_LENGTH_ACCOUNT_NAME + " characters.");
        }
    }

    public void passwordValidation(String password, String confirmation) throws Exception {
        if (password == null || confirmation == null) {
            throw new Exception("Please enter and confirm your password");
        }
        if (!password.equals(confirmation)) {
            throw new Exception("The passwords does not match");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new Exception("Your password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
        }
    }
}
