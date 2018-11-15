package com.example.sridharjajoo.newsapp;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sridharjajoo.newsapp.core.FavouriteFragment;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineFragment;
import com.example.sridharjajoo.newsapp.core.Search.SearchFragment;
import com.example.sridharjajoo.newsapp.core.Setttings.SettingsActivity;

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
        showHeadlines();

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
