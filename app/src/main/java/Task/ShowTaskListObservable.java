package Task;

import android.app.Activity;

import java.util.Observable;
import java.util.Observer;

public class ShowTaskListObservable implements Observer {
    Activity activity;

    public ShowTaskListObservable(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void update(Observable o, Object arg) {
        TaskListObserved taskListObserved = (TaskListObserved) o;
    }





}
