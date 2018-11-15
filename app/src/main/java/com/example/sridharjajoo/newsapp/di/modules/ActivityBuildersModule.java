package com.example.sridharjajoo.newsapp.di.modules;

import com.example.sridharjajoo.newsapp.NewsMainActivity;
import com.example.sridharjajoo.newsapp.core.Favourite.FavouriteFragment;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineFragment;
import com.example.sridharjajoo.newsapp.core.Headline.NewsDetailActivity;
import com.example.sridharjajoo.newsapp.core.Search.SearchFragment;
import com.example.sridharjajoo.newsapp.core.Setttings.SettingsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NewsMainActivity contributesNewsMainActivity();

    @ContributesAndroidInjector
    abstract NewsDetailActivity contributesNewsDetailActivity();

    @ContributesAndroidInjector
    abstract FavouriteFragment contributesFavouriteFragment();

    @ContributesAndroidInjector
    abstract HeadlineFragment contributesHeadlineFragment();

    @ContributesAndroidInjector
    abstract SearchFragment contributesSearchFragment();

    @ContributesAndroidInjector
    abstract SettingsActivity contributesSettingsActivity();
}
