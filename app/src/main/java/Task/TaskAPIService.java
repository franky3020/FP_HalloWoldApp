package Task;
import okhttp3.MediaType;

public class TaskAPIService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LOG_TAG = TaskAPIService.class.getSimpleName();

    public static final String API_version = "ms-provider-release-200";

    public static final String base_URL = "http://140.134.26.65:46557/" + API_version + "/tasks";

    public interface GetAPIListener<T> {
        void onResponseOK(T t);
        void onFailure();
    }

    public void getATask(final int taskID, final GetAPIListener<Task> getAPIListener) {
        Task task = TaskBuilder.aTask('5', 500, 66).build();
        getAPIListener.onResponseOK(task);
    }

}
