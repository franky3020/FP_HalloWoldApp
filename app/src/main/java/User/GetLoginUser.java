package User;

import android.app.Activity;
import android.content.Intent;

import com.example.my_first_application.LoginActivity;

public class GetLoginUser {

    private static GetLoginUser instance = new GetLoginUser();
    private User user;
    private boolean isLogin = false;

    private GetLoginUser() {

    }

    public static User getLoginUser() {
        if (instance.isLogin) {
            return instance.user;
        } else {
            return null;
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
    }

    public static void unRegisterUser() {
        instance.user = null;
        instance.isLogin = false;
    }

}
