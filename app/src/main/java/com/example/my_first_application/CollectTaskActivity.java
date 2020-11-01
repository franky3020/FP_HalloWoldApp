package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class CollectTaskActivity extends AppCompatActivity {

    private static final String LOG_TAG = CollectTaskActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_task);

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
        public Fragment getItem(int position) {
            return TaskDetailFragment.newInstance("1", "2");
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }





}