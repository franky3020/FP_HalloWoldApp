package TaskState;

import TaskState.ITaskStateContext;


public class BoosReleaseState implements ITaskStateAction {

    private static BoosReleaseState instance = new BoosReleaseState();
    private BoosReleaseState() {

    }
    public static BoosReleaseState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        if(context.isReleaseUser()) {
            context.addSelectedWorkerButton();
            context.addDeleteButton();
        }
    }
}
