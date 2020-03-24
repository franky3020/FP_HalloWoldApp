package Task;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import User.User;

import static org.junit.Assert.*;


public class TaskTest {
    @Test
    public void doConstruct(){
        User user1 = new User("May",1,"0");
        User user2 = new User("John",2,"1");

        Date date = new GregorianCalendar(2020, 3, 24).getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);  //use java.util.Date object as arguement
        Task task = new Task("Buying Lunch",1,user1,user2,1,date);

        assertEquals("Buying Lunch",task.getTaskName());
        assertEquals(1,task.getTaskID());
        assertEquals("May",user1.getUserName());
        assertEquals("John",user2.getUserName());
        assertEquals(2020, cal.get(Calendar.YEAR));
        assertEquals(3, cal.get(Calendar.MONTH));
        assertEquals(24, cal.get(Calendar.DATE));
        assertEquals(1,task.getTaskState());

    }
    @Test
    public void doSet(){
        User user1 = new User("May",1,"0");
        User user2 = new User("John",2,"1");

        Date date = new GregorianCalendar(2020, 3, 24).getTime();

        Task task = new Task("Buying Lunch", 1, user1, user2,1, date);

        task.setTaskName("Cleaning room");
        task.setTaskID(2);
        user1.setUserName("Jack");
        user2.setUserName("Mary");
        task.setTaskState(1);

        assertEquals("Cleaning room",task.getTaskName());
        assertEquals(2,task.getTaskID());
        assertEquals("Jack",user1.getUserName());
        assertEquals("Mary",user2.getUserName());
        assertEquals(1,task.getTaskState());

    }
}
