package User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void constructTest() {

        User assignerUser = new User("May",1,"0");
        User accepterUser = new User("John",2,"1");

        assignerUser.setUserName("May");
        assertEquals("May", assignerUser.getUserName());

        assignerUser.setUserID(1);
        assertEquals(1, assignerUser.getUserID());

        assignerUser.setUserPhone("0");
        assertEquals("0", assignerUser.getUserPhone());

        accepterUser.setUserName("John");
        assertEquals("John", accepterUser.getUserName());

        accepterUser.setUserID(2);
        assertEquals(2, accepterUser.getUserID());

        accepterUser.setUserPhone("1");
        assertEquals("1", accepterUser.getUserPhone());
    }


}
