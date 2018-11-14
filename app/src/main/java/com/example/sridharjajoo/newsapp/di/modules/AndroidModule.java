package com.example.sridharjajoo.newsapp.di.modules;

import android.content.Context;

import com.example.sridharjajoo.newsapp.NewsMainApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    @Provides
    @Singleton
    Context providesContext() {
        return NewsMainApplication.context;
    }

}
