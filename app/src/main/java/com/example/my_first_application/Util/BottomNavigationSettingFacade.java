package com.example.my_first_application.Util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.my_first_application.ChatActivity;
import com.example.my_first_application.HomePageActivity;
import com.example.my_first_application.ProfileActivity;
import com.example.my_first_application.R;
import com.example.my_first_application.ShowTaskActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public final class BottomNavigationSettingFacade {

    public static void setReleaseModeNavigation(final Activity activity, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.inflateMenu(R.menu.menu_release_mode_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        startAnotherActivity(activity, ShowTaskActivity.class);
                        break;

                    case R.id.icon_message:
                        startAnotherActivity(activity, ChatActivity.class);
                        break;

                    case R.id.icon_profile:
                        startAnotherActivity(activity, ProfileActivity.class);
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
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        startAnotherActivity(activity, HomePageActivity.class);
                        break;

                    case R.id.icon_search:
                        return true;

                    case R.id.icon_message:
                        startAnotherActivity(activity, ChatActivity.class);
                        break;

                    case R.id.icon_profile:
                        startAnotherActivity(activity, ProfileActivity.class);
                        break;
                }
                return true;
            }
        });

    }

    private static void startAnotherActivity(Activity activity, Class activityClass) {
        if ( activity.getClass() != activityClass ) {
            Intent intent = new Intent();
            intent.setClass(activity, activityClass);
            activity.startActivity(intent);
            activity.finish();
        }
    }

}