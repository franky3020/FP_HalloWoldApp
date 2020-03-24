package Task;

import org.junit.Test;

import User.User;

import static org.junit.Assert.*;


public class Tasktest {
    @Test
    public void doConstruct(){
        TaskTime taskTime = new TaskTime("2020", "03", "24");
        User user1 = new User("May",1,"0");
        User user2 = new User("John",2,"1");
        Task task = new Task("Buying Lunch",1,user1,user2,1,taskTime);

        assertEquals("Buying Lunch",task.getTaskName());
        assertEquals(1,task.getTaskID());
        assertEquals("May",user1.getUserName());
        assertEquals("John",user2.getUserName());
        assertEquals("2020",taskTime.getYear());
        assertEquals("03",taskTime.getMonth());
        assertEquals("24",taskTime.getDay());
        assertEquals(1,task.getTaskState());

    }
    @Test
    public void doSet(){
        TaskTime taskTime = new TaskTime("2020", "03", "24");
        User user1 = new User("May",1,"0");
        User user2 = new User("John",2,"1");
        Task task = new Task("Buying Lunch",1,user1,user2,1,taskTime);

        task.setTaskName("Cleaning room");
        task.setTaskID(2);
        user1.setUserName("Jack");
        user2.setUserName("Mary");
        task.setTaskState(1);
        taskTime.setYear("2020");
        taskTime.setMonth("03");
        taskTime.setDay("22");

        assertEquals("Cleaning room",task.getTaskName());
        assertEquals(2,task.getTaskID());
        assertEquals("Jack",user1.getUserName());
        assertEquals("Mary",user2.getUserName());
        assertEquals(1,task.getTaskState());
        assertEquals("2020",taskTime.getYear());
        assertEquals("03",taskTime.getMonth());
        assertEquals("22",taskTime.getDay());

    }
}
