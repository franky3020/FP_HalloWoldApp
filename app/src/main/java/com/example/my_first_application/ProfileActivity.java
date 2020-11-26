package com.example.my_first_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import User.GetLoginUser;
import User.User;
import User.UserAPIService;
import User.UserBuilder;

public class ProfileActivity extends AppCompatActivity {

    Button userModeSwitchBtn;

    User user = UserBuilder.anUser(0).build(); // 初始化一個假的
    Handler getUsersAPI_Handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        userModeSwitchBtn = findViewById(R.id.button_profile_switch_mode);

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationSettingFacade.setNavigation(this, bottomNavigationView); // 會依據不同使用者狀態做設定

        userModeSwitchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GetLoginUser.isReleaseMode()) {
                    GetLoginUser.setUserMode(GetLoginUser.RECEIVE_MODE_STR);
                } else if (GetLoginUser.isReceiveMode()){
                    GetLoginUser.setUserMode(GetLoginUser.RELEASE_MODE_STR);
                }
                finish();
                startActivity(getIntent());
            }
        });

        user = GetLoginUser.getLoginUser();

        if(user !=  null) {
            TextView memberID = findViewById(R.id.textView_memberID);
            memberID.setText( user.getName() + " 你好" );

            TextView memberPointNumber = findViewById(R.id.textView_member_points_number);
            memberPointNumber.setText("" + user.getPoint());

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getUsersAPI_Handler.post(getUsersAPI_Runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.getUsersAPI_Handler.removeCallbacks(getUsersAPI_Runnable);
    }

    private final Runnable getUsersAPI_Runnable = new Runnable() {
        public void run() {
            sendGetUsersAPI();
            int delayMillis = 3000;
            getUsersAPI_Handler.postDelayed(getUsersAPI_Runnable, delayMillis);
        }
    };
    private void sendGetUsersAPI() {
        UserAPIService userAPIService = new UserAPIService();
        userAPIService.getAUserByUserID(user.getId(), new UserAPIService.UserListener() {
            @Override
            public void onResponseOK(final User user) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView memberID = findViewById(R.id.textView_memberID);
                        memberID.setText( user.getName() + " 你好" );

                        TextView memberPointNumber = findViewById(R.id.textView_member_points_number);
                        memberPointNumber.setText("" + user.getPoint());

                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });

    }

    public void onClickToSignOut(View view) {
        GetLoginUser.unRegisterUser();

        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }

}
