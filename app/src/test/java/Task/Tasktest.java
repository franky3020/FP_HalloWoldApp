package Task;

import org.junit.Test;

import static org.junit.Assert.*;


public class Tasktest {
    @Test
    public void doSet(){
        Task task = new Task();

        task.setTaskName("Cleaning room");
        task.setTaskID("A001");

        assertEquals("Cleaning room",task.getTaskName());
        assertEquals("A001",task.getTaskID());

    }
}
