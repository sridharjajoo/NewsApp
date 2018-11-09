package com.example.sridharjajoo.newsapp.di;

import com.example.sridharjajoo.newsapp.NewsMainApplication;
import com.example.sridharjajoo.newsapp.di.modules.ActivityBuildersModule;
import com.example.sridharjajoo.newsapp.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        ActivityBuildersModule.class,
        AndroidSupportInjectionModule.class,
        AppModule.class})

public interface AppComponent extends AndroidInjector<NewsMainApplication> {

    void inject(NewsMainApplication newsMainApplication);
}
