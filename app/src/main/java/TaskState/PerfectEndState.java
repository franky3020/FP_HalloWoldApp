package TaskState;

public class PerfectEndState implements ITaskStateAction {

    private static PerfectEndState instance = new PerfectEndState();
    private PerfectEndState() {

    }
    public static PerfectEndState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {

    }

    @Override
    public String toString() {
        return "PerfectEndState{}";
    }
}
