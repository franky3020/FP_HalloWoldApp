package Task;

import android.app.Activity;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

public class ShowTaskListObservable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        TaskListObserved taskListObserved = (TaskListObserved) o;
        taskListObserved.updateAllTasks();
    }

}
