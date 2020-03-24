package Task;

import org.junit.Test;

import static org.junit.Assert.*;


public class Tasktest {
    @Test
    public void doConstruct(){
        Task task = new Task("Buying Lunch","A001","May","John");

        assertEquals("Buying Lunch",task.getTaskName());
        assertEquals("A001",task.getTaskID());
        assertEquals("May",task.getTaskAssigner());
        assertEquals("John",task.getTaskAccepter());
        assertEquals(0,task.getTaskState());
        assertEquals("2020",task.getYear());
        assertEquals("03",task.getMonth());
        assertEquals("24",task.getDay());

    }
    @Test
    public void doSet(){
        Task task = new Task("Buying Lunch","A001","May","John");

        task.setTaskName("Cleaning room");
        task.setTaskID("A002");
        task.setTaskAssigner("Jack");
        task.setTaskAccepter("Mary");
        task.setTaskState(1);
        task.setYear("2020");
        task.setMonth("03");
        task.setDay("22");

        assertEquals("Cleaning room",task.getTaskName());
        assertEquals("A002",task.getTaskID());
        assertEquals("Jack",task.getTaskAssigner());
        assertEquals("Mary",task.getTaskAccepter());
        assertEquals(1,task.getTaskState());
        assertEquals("2020",task.getYear());
        assertEquals("03",task.getMonth());
        assertEquals("22",task.getDay());

    }
}
