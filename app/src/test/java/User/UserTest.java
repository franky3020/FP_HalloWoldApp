package User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void constructTest() {

        User assignerUser = new User("May",1,"0");
        User accepterUser = new User("John",2,"1");

        assignerUser.setName("May");
        assertEquals("May", assignerUser.getName());

        assignerUser.setId(1);
        assertEquals(1, assignerUser.getId());

        assignerUser.setPhone("0");
        assertEquals("0", assignerUser.getPhone());

        accepterUser.setName("John");
        assertEquals("John", accepterUser.getName());

        accepterUser.setId(2);
        assertEquals(2, accepterUser.getId());

        accepterUser.setPhone("1");
        assertEquals("1", accepterUser.getPhone());
    }


}
