package login;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void doLogin() {
        Login login = new Login();
        assertEquals("Correct!",login.ConfirmLogin("aaaa","bbbb"));
        assertEquals("Wrong username or password!",login.ConfirmLogin("bbbb","aaaa"));

    }
}
