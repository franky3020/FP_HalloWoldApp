package TaskState;

public class FailureEndState implements ITaskStateAction {

    private static FailureEndState instance = new FailureEndState();
    private FailureEndState() {

    }
    public static FailureEndState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {

    }

    @Override
    public String toString() {
        return "FailureEndState{}";
    }
}

