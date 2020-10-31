package TaskState;


public class BoosReleaseState implements ITaskStateAction {

    private static BoosReleaseState instance = new BoosReleaseState();
    private BoosReleaseState() {

    }
    public static BoosReleaseState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        context.removeAllViewForTaskStateContext();
        context.addATaskStateShow();

        if (context.isReleaseUser()) {
            context.addBoosSelectedWorkerButton();
            context.addBoosDeleteButton();

        } else if (context.isCanRequestTaskUser()) {
            context.addWorkerRequestTaskButton();
        } else if (context.isCanCancelRequestTaskUser()) {
            context.addWorkerCancelRequestButton();
        }
    }

    @Override
    public String toString() {
        return "BoosReleaseState{}";
    }
}
