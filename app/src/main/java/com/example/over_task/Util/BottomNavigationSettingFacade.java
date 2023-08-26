package com.example.over_task.Util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.over_task.CollectTaskActivity;
import com.example.over_task.HomePageActivity;
import com.example.over_task.ProfileActivity;

import com.example.over_task.R;
import com.example.over_task.ShowChatActivity;
import com.example.over_task.ShowTaskActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import User.GetLoginUser;

public final class BottomNavigationSettingFacade {

    public static void setNavigation(final Activity activity, BottomNavigationView bottomNavigationView) {
        if (GetLoginUser.isReleaseMode()) {
            setReleaseModeNavigation(activity, bottomNavigationView);
        } else if(GetLoginUser.isReceiveMode()) {
            setReceiveModeNavigation(activity, bottomNavigationView);
        } else if(GetLoginUser.isVisitorsMode()) {
            setReceiveModeNavigation(activity, bottomNavigationView);
        }
    }


    public static void setReleaseModeNavigation(final Activity activity, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.inflateMenu(R.menu.menu_release_mode_bottom_navigation);

        if (activity.getClass() == ShowTaskActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.icon_home);

        } else if (activity.getClass() == ShowChatActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.icon_message);

        } else if (activity.getClass() == ProfileActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.icon_profile);

        } else {
            // no thing
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        startAnotherActivity(activity, ShowTaskActivity.class);
                        break;

                    case R.id.icon_message:
                        startAnotherActivity(activity, ShowChatActivity.class);
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


        if (activity.getClass() == HomePageActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.icon_home);

        } else if (activity.getClass() == CollectTaskActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.icon_search);

        } else if (activity.getClass() == ShowChatActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.icon_message);

        } else if (activity.getClass() == ProfileActivity.class) {
            bottomNavigationView.setSelectedItemId(R.id.icon_profile);
        } else {
            // no thing
        }




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icon_home:
                        startAnotherActivity(activity, HomePageActivity.class);
                        break;

                    case R.id.icon_search: // Todo 要改名子
                        startAnotherActivity(activity, CollectTaskActivity.class);
                        break;

                    case R.id.icon_message:
                        startAnotherActivity(activity, ShowChatActivity.class);
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
