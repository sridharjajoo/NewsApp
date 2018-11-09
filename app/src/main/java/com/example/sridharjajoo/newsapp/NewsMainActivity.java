package com.example.sridharjajoo.newsapp;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sridharjajoo.newsapp.core.FavouriteFragment;
import com.example.sridharjajoo.newsapp.core.HeadlineFragment;
import com.example.sridharjajoo.newsapp.core.SearchFragment;

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
    }

    private void showNavigation() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
