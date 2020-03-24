package Task;

import org.junit.Test;

import static org.junit.Assert.*;


public class Tasktest {
    @Test
    public void doConstruct(){
        TaskTime taskTime = new TaskTime("2020", "03", "24");
        Task task = new Task("Buying Lunch","A001","May","John", 1, taskTime);

        assertEquals("Buying Lunch",task.getTaskName());
        assertEquals("A001",task.getTaskID());
        assertEquals("May",task.getTaskAssigner());
        assertEquals("John",task.getTaskAccepter());
        assertEquals(1,task.getTaskState());
        assertEquals("2020",taskTime.getYear());
        assertEquals("03",taskTime.getMonth());
        assertEquals("24",taskTime.getDay());

    }
    @Test
    public void doSet(){
        TaskTime taskTime = new TaskTime("2020", "03", "24");
        Task task = new Task("Buying Lunch","A001","May","John", 1, taskTime);

        task.setTaskName("Cleaning room");
        task.setTaskID("A002");
        task.setTaskAssigner("Jack");
        task.setTaskAccepter("Mary");
        task.setTaskState(1);
        taskTime.setYear("2020");
        taskTime.setMonth("03");
        taskTime.setDay("22");

        assertEquals("Cleaning room",task.getTaskName());
        assertEquals("A002",task.getTaskID());
        assertEquals("Jack",task.getTaskAssigner());
        assertEquals("Mary",task.getTaskAccepter());
        assertEquals(1,task.getTaskState());
        assertEquals("2020",taskTime.getYear());
        assertEquals("03",taskTime.getMonth());
        assertEquals("22",taskTime.getDay());

    }
}
