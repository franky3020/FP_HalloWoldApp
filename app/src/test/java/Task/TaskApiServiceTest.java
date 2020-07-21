package Task;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskApiServiceTest {

    @Test
    public void post_test() {
        TaskApiService taskApiService = new TaskApiService();
        taskApiService.post();
    }
}