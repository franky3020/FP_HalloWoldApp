package User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void constructTest() {

        User assignerUser = new User("May",1,"0");
        User accepterUser = new User("John",2,"1");
        assertEquals("May", assignerUser.getUserName());
    }


}
