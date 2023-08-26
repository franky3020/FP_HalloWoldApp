package com.example.my_first_application.Util;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.my_first_application.ChatActivity;
import com.example.my_first_application.CollectTaskActivity;
import com.example.my_first_application.HomePageActivity;
import com.example.my_first_application.ProfileActivity;

import com.example.my_first_application.R;
import com.example.my_first_application.ShowChatActivity;
import com.example.my_first_application.ShowTaskActivity;
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
