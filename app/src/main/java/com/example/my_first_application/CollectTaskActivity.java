package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class CollectTaskActivity extends AppCompatActivity {

    private static final String LOG_TAG = CollectTaskActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationSettingFacade.setReceiveModeNavigation(this, bottomNavigationView);


        CollectTaskAdapter collectTaskAdapter = new CollectTaskAdapter(getSupportFragmentManager(),
                                                               FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(collectTaskAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


    private class CollectTaskAdapter extends FragmentStatePagerAdapter {

        public CollectTaskAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) { // Todo 好像也能用 TaskDetailFragment.newInstance("1", "2") 來初始化
            return new ShowRecyclerViewTaskFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "已申請"; // Todo 之後要用 getResources().getText(R.string.XXX);
                case 1:
                    return "進行中";
                case 2:
                    return "已完成";
                default:
                    return null;
            }
        }
    }





}