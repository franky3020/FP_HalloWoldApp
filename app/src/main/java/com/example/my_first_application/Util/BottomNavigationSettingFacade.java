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
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        if ( activity.getClass() == ShowTaskActivity.class ) {
                            return true;
                        } else {
                            intent.setClass(activity, ShowTaskActivity.class);
                        }
                        break;

                    case R.id.icon_message:
                        if ( activity.getClass() == ChatActivity.class ) {
                            return true;
                        } else {
                            intent.setClass(activity, ChatActivity.class);
                        }
                        break;

                    case R.id.icon_profile:
                        if ( activity.getClass() == ProfileActivity.class ) {
                            return true;
                        } else {
                            intent.setClass(activity, ProfileActivity.class);
                        }
                        break;
                }
                activity.startActivity(intent);
                activity.finish();

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
                        if ( activity.getClass() == HomePageActivity.class ) {
                            return true;
                        } else {
                            intent.setClass(activity, HomePageActivity.class);
                        }
                        break;

                    case R.id.icon_search:
                        return true;

                    case R.id.icon_message:
                        if ( activity.getClass() == ChatActivity.class ) {
                            return true;
                        } else {
                            intent.setClass(activity, ChatActivity.class);
                        }
                        break;

                    case R.id.icon_profile:
                        if ( activity.getClass() == ProfileActivity.class ) {
                            return true;
                        } else {
                            intent.setClass(activity, ProfileActivity.class);
                        }
                        break;
                }

                activity.startActivity(intent);
                activity.finish();
                return true;
            }
        });

    }

    private void startAnotherActivity(Activity activity, Class<Activity> activityClass) {
        Intent intent = new Intent();
        if ( activity.getClass() != activityClass ) {
            intent.setClass(activity, activityClass);
        }
        activity.startActivity(intent);
        activity.finish();
    }








}
