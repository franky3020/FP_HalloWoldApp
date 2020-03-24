package Task;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import User.User;

import static org.junit.Assert.*;


public class TaskTest {
    @Test
    public void constructTest() {

        User assignerUser = new User("May",1,"0");
        User accepterUser = new User("John",2,"1");
        Date date = new GregorianCalendar(2020, 3, 24).getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);  //use java.util.Date object as arguement
        Task task = new Task("Buying Lunch",1, assignerUser, accepterUser,1, date);

        assertEquals("Buying Lunch",task.getTaskName());
        assertEquals(1,task.getTaskID());
        assertEquals(1,task.getTaskState());
        assertEquals(2020, cal.get(Calendar.YEAR));
        assertEquals(3, cal.get(Calendar.MONTH));
        assertEquals(24, cal.get(Calendar.DATE));
    }
    @Test
    public void setTest() {

        User assignerUser = new User("May",1,"0");
        User accepterUser = new User("John",2,"1");
        Date date = new GregorianCalendar(2020, 3, 24).getTime();
        Task task = new Task("Buying Lunch", 1, assignerUser, accepterUser,1, date);

        task.setTaskName("Cleaning room");
        assertEquals("Cleaning room", task.getTaskName());

        task.setTaskID(2);
        assertEquals(2, task.getTaskID());

        assignerUser.setUserName("Jack");
        assertEquals("Jack", assignerUser.getUserName());

        accepterUser.setUserName("Mary");
        assertEquals("Mary", accepterUser.getUserName());

        task.setTaskState(1);
        assertEquals(1, task.getTaskState());
    }
}
