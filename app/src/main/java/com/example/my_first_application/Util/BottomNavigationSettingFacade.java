package com.example.my_first_application.Util;

import android.app.Activity;

import com.example.my_first_application.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationSettingFacade {

    public static void setReleaseModeNavigation(Activity activity, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.inflateMenu(R.menu.menu_main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationItemListener(activity));
    }

    public static void setReceiveModeNavigation(Activity activity, BottomNavigationView bottomNavigationView) {

    }







}
