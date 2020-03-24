package LoginManager;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginManagerTest {

    @Test
    public void confirmLoginTest() {
        LoginManager login = new LoginManager();
        assertEquals(true,login.confirmLogin("test_username","test_password"));
        assertEquals(false,login.confirmLogin("test_username","test_error_password"));
    }
}
