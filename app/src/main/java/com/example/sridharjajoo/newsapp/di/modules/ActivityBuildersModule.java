package com.example.sridharjajoo.newsapp.di.modules;

import com.example.sridharjajoo.newsapp.NewsMainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NewsMainActivity contributesNewsMainActivity();
}
