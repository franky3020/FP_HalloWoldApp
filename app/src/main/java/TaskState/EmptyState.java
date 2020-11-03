package TaskState;

public class EmptyState implements ITaskStateAction {

    private static EmptyState instance = new EmptyState();
    private EmptyState() {

    }
    public static EmptyState getInstance() {
        return instance;
    }

    @Override
    public void showUI(ITaskStateContext context) {
        // do nothing
    }

    @Override
    public String toString() {
        return "EmptyState{}";
    }
}
