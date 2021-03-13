package com.example.oo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.oo.R;
import com.example.oo.fragment.MainFragment;
import com.example.oo.fragment.SelfFragment;
import com.example.oo.fragment.WeatherFragment;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager main_content;
    TabLayout tabLayout;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitle = new ArrayList<>();
    MainFragment mainFragment = new MainFragment();
    SelfFragment selfFragment = new SelfFragment();
    WeatherFragment weatherFragment = new WeatherFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_content = findViewById(R.id.main_content);
        tabLayout = findViewById(R.id.bottom_tabs_1);

        fragmentList.clear();
        fragmentTitle.clear();
        fragmentTitle.add("天气");
        fragmentTitle.add("发现");
        fragmentTitle.add("我的");
        fragmentList.add(weatherFragment);
        fragmentList.add(mainFragment);
        fragmentList.add(selfFragment);

        main_content.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),
                ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tabLayout.setupWithViewPager(main_content);
        main_content.setOffscreenPageLimit(3);
        main_content.setCurrentItem(1);//设置初始位置

        //要在绑定后进行图片加入
        TabLayout.Tab tab_weather = tabLayout.getTabAt(0);
        TabLayout.Tab tab_main = tabLayout.getTabAt(1);
        TabLayout.Tab tab_self = tabLayout.getTabAt(2);
        tab_weather.setIcon(getResources().getDrawable(R.drawable.ic_baseline_cloud_queue_24));
        tab_main.setIcon(getResources().getDrawable(R.drawable.ic_baseline_add_circle_24));
        tab_self.setIcon(getResources().getDrawable(R.drawable.ic_baseline_face_24));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

}