package com.example.sridharjajoo.newsapp.di.modules;

import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineServiceImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ModelModule {

    @Binds
    @Singleton
    abstract HeadlineService bindsHeadlineServiceModule(HeadlineServiceImpl headlineService);

}
