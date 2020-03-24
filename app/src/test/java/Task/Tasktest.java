package Task;

import org.junit.Test;

import User.User;

import static org.junit.Assert.*;


public class Tasktest {
    @Test
    public void doConstruct(){
        User user1 = new User("May",1,"0");
        User user2 = new User("John",2,"1");
        Task task = new Task("Buying Lunch",1,user1,user2);

        assertEquals("Buying Lunch",task.getTaskName());
        assertEquals(1,task.getTaskID());
        assertEquals("May",user1.getUserName());
        assertEquals("John",user2.getUserName());
        assertEquals(0,task.getTaskState());
        assertEquals("2020",task.getYear());
        assertEquals("03",task.getMonth());
        assertEquals("24",task.getDay());

    }
    @Test
    public void doSet(){
        User user1 = new User("May",1,"0");
        User user2 = new User("John",2,"1");
        Task task = new Task("Buying Lunch",1,user1,user2);

        task.setTaskName("Cleaning room");
        task.setTaskID(2);
        user1.setUserName("Jack");
        user2.setUserName("Mary");
        task.setTaskState(1);
        task.setYear("2020");
        task.setMonth("03");
        task.setDay("22");

        assertEquals("Cleaning room",task.getTaskName());
        assertEquals(2,task.getTaskID());
        assertEquals("Jack",user1.getUserName());
        assertEquals("Mary",user2.getUserName());
        assertEquals(1,task.getTaskState());
        assertEquals("2020",task.getYear());
        assertEquals("03",task.getMonth());
        assertEquals("22",task.getDay());

    }
}
