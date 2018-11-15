package com.example.sridharjajoo.newsapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sridharjajoo.newsapp.core.Favourite.FavouriteFragment;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineFragment;
import com.example.sridharjajoo.newsapp.core.Search.SearchFragment;
import com.example.sridharjajoo.newsapp.core.Setttings.SettingsActivity;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class NewsMainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        showNavigation();
        setAlarm();
        showHeadlines();
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
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_headline:
                    HeadlineFragment navigationFragment = new HeadlineFragment();
                    actionBar.setTitle("Headlines");
                    loadFragment(navigationFragment);
                    return true;

                case R.id.navigation_search:
                    SearchFragment searchFragment = new SearchFragment();
                    actionBar.setTitle("Search");
                    loadFragment(searchFragment);
                    return true;

                case R.id.navigation_favourite:
                    FavouriteFragment favouriteFragment = new FavouriteFragment();
                    actionBar.setTitle("Favourite");
                    loadFragment(favouriteFragment);
                    return true;
                default:
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

}
