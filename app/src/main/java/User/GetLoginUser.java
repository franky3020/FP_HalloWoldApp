package User;

import android.app.Activity;
import android.content.Intent;

import com.example.my_first_application.LoginActivity;

public class GetLoginUser {

    private static GetLoginUser instance = new GetLoginUser();
    private User user;
    private boolean isLogin = false;

    public static final String RELEASE_MODE_STR = "releaseMode";
    public static final String RECEIVE_MODE_STR = "receiveMode";
    public static final String VISITORS_MODE_STR = "visitorsMode";
    private String userMode = VISITORS_MODE_STR;

    private GetLoginUser() {

    }

    public static User getLoginUser() {
        if (instance.isLogin) {
            return instance.user;
        } else { // 以防回傳null 所以做個假的
            return UserBuilder.anUser(-1)
                    .withName("Not Login user")
                    .build();
        }
    }

    public static void checkLoginIfNotThenGoToLogin(Activity activity) {
        if (instance.isLogin != true) {
            Intent intent = new Intent();
            intent.setClass(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public static void registerUser(User user) {
        instance.user = user;
        instance.isLogin = true;
        instance.userMode = RELEASE_MODE_STR;
    }

    public static void unRegisterUser() {
        instance.user = null;
        instance.isLogin = false;
    }

    public static boolean isReleaseMode() {
        if (instance.userMode.equals(RELEASE_MODE_STR)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isReceiveMode() {
        if (instance.userMode.equals(RECEIVE_MODE_STR)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isVisitorsMode() {
        if (instance.userMode.equals(VISITORS_MODE_STR)) {
            return true;
        } else {
            return false;
        }
    }

    public static void setUserMode(String userMode) {
        instance.userMode = userMode;
    }

    public static boolean isLogin() {
        return instance.isLogin;
    }
}
