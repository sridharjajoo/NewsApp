package com.example.sridharjajoo.newsapp.di.modules;

import dagger.Module;

@Module(includes = {ModelModule.class, NetworkModule.class, AndroidModule.class, ViewModelModule.class
})
public class AppModule {

}
