package com.example.my_first_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import User.GetLoginUser;
import User.User;

public class ProfileActivity extends AppCompatActivity {

    Button userModeSwitchBtn;

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

        // 以下為測試用, 如果系統有被登入 則會修改 memberPoints 的文字
        User loginUser = GetLoginUser.getLoginUser();
        if(loginUser !=  null) {
            TextView memberPoints = findViewById(R.id.textView_member_points);
            memberPoints.setText( loginUser.getName()  + " 你好");

            TextView memberPointNumber = findViewById(R.id.textView_member_points_number);
            memberPointNumber.setText("" + loginUser.getPoint());

        }
        ////////////////////////////////////////////////////////////////////////

    }

    public void onClickToTestMain(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickToSignOut(View view) {
        GetLoginUser.unRegisterUser();

        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }

}
