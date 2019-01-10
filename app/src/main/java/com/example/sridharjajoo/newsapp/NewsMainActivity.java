package com.example.sridharjajoo.newsapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.sridharjajoo.newsapp.core.Category.CategoryFragment;
import com.example.sridharjajoo.newsapp.core.Favourite.FavouriteFragment;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineFragment;
import com.example.sridharjajoo.newsapp.core.Search.SearchFragment;
import com.example.sridharjajoo.newsapp.core.Setttings.SettingsActivity;
import com.example.sridharjajoo.newsapp.databinding.ActivityNewsMainBinding;

import java.lang.reflect.Field;
import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class NewsMainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private ActionBar actionBar;
    private ActivityNewsMainBinding binding;
    private int TOTALCATEGORY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_main);
        actionBar = getSupportActionBar();
        showNavigation();
        setAlarm();
        showHeadlines();
        BottomNavigationViewHelper.disableShiftMode(binding.navigation);
    }

    private void setAlarm() {
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(NewsMainActivity.this,
                AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                NewsMainActivity.this, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to start at 10:00 AM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 86400000, // for repeating
                pendingIntent);
    }

    private void showHeadlines() {
        HeadlineFragment navigationFragment = new HeadlineFragment();
        actionBar.setTitle("Headlines");
        loadFragment(navigationFragment);
    }

    private void showNavigation() {
        binding.navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_headline:
                    binding.frameContainer.setVisibility(View.VISIBLE);
                    binding.vp.setVisibility(View.GONE);
                    binding.tabLayout.setVisibility(View.GONE);
                    HeadlineFragment navigationFragment = new HeadlineFragment();
                    actionBar.setTitle("Headlines");
                    loadFragment(navigationFragment);
                    return true;

                case R.id.navigation_search:
                    actionBar.setTitle("Search");
                    binding.frameContainer.setVisibility(View.VISIBLE);
                    binding.vp.setVisibility(View.GONE);
                    binding.tabLayout.setVisibility(View.GONE);
                    SearchFragment searchFragment = new SearchFragment();
                    loadFragment(searchFragment);
                    return true;

                case R.id.navigation_category:
                    actionBar.setTitle("Category");
                    binding.frameContainer.setVisibility(View.GONE);
                    binding.vp.setVisibility(View.VISIBLE);
                    binding.tabLayout.setVisibility(View.VISIBLE);
                    binding.vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                    binding.tabLayout.setupWithViewPager(binding.vp);
                    return true;

                case R.id.navigation_favourite:
                    binding.frameContainer.setVisibility(View.VISIBLE);
                    binding.vp.setVisibility(View.GONE);
                    binding.tabLayout.setVisibility(View.GONE);
                    FavouriteFragment favouriteFragment = new FavouriteFragment();
                    actionBar.setTitle("Favourite");
                    loadFragment(favouriteFragment);
                    return true;

                default:
                    binding.frameContainer.setVisibility(View.VISIBLE);
                    binding.vp.setVisibility(View.GONE);
                    binding.tabLayout.setVisibility(View.GONE);
                    HeadlineFragment navigationFragmentDefault = new HeadlineFragment();
                    actionBar.setTitle("Headlines");
                    loadFragment(navigationFragmentDefault);
                    return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(NewsMainActivity.this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Science";
                case 1:
                    return "Sports";
                case 2:
                    return "Entertainment";
            }
            return " ";

        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("search", position);
            CategoryFragment categoryFragment = new CategoryFragment();
            categoryFragment.setArguments(bundle);
            switch (position) {
                case 0:
                    return categoryFragment;
                case 1:
                    return categoryFragment;
                case 2:
                    return categoryFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return TOTALCATEGORY;
        }
    }

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
}
