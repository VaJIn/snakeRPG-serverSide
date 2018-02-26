import fr.vajin.snakerpg.form.LoginFormLogic;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class LoginFormLogicTest {

    LoginFormLogic loginFormLogic = new LoginFormLogic(new DummyDataBaseAccess());

    @Test
    @DisplayName("Test Account Name Validation")
    public void testAccountNameValidation() {
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.accountNameValidation(null), LoginFormLogic.ERROR_NO_ACCOUNT_NAME);
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.accountNameValidation(""), LoginFormLogic.ERROR_NO_ACCOUNT_NAME);
        Assertions.assertAll(() -> loginFormLogic.accountNameValidation("anAccountName"));
    }

    @Test
    @DisplayName("Test Password Validation")
    public void testPasswordValidation() {
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.passwordValidation(null), LoginFormLogic.ERROR_NO_PASSWORD);
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.passwordValidation(""), LoginFormLogic.ERROR_NO_PASSWORD);
        Assertions.assertAll(() -> loginFormLogic.passwordValidation("apassword"));
    }


}
