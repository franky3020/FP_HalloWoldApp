package com.example.my_first_application.Util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.my_first_application.ChatActivity;
import com.example.my_first_application.HomePageActivity;
import com.example.my_first_application.ProfileActivity;
import com.example.my_first_application.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationSettingFacade {

    public static void setReleaseModeNavigation(final Activity activity, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.inflateMenu(R.menu.menu_release_mode_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        intent.setClass(activity, HomePageActivity.class);
                        activity.startActivity(intent);
                        break;

                    case R.id.icon_message:
                        intent.setClass(activity, ChatActivity.class);
                        activity.startActivity(intent);
                        break;

                    case R.id.icon_profile:
                        intent.setClass(activity, ProfileActivity.class);
                        activity.startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    public static void setReceiveModeNavigation(final Activity activity, BottomNavigationView bottomNavigationView) {

        bottomNavigationView.inflateMenu(R.menu.menu_main_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        intent.setClass(activity, HomePageActivity.class);
                        activity.startActivity(intent);
                        break;

                    case R.id.icon_search:
                        break;

                    case R.id.icon_message:
                        intent.setClass(activity, ChatActivity.class);
                        activity.startActivity(intent);
                        break;

                    case R.id.icon_profile:
                        intent.setClass(activity, ProfileActivity.class);
                        activity.startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }







}
