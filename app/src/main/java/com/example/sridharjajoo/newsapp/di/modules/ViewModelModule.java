package com.example.sridharjajoo.newsapp.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.sridharjajoo.newsapp.core.Category.CategoryViewModel;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineViewModel;
import com.example.sridharjajoo.newsapp.core.Search.SearchViewModel;
import com.example.sridharjajoo.newsapp.di.NewsViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    public abstract ViewModel bindsSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel.class)
    public abstract ViewModel bindsCategoryViewModel(CategoryViewModel categoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HeadlineViewModel.class)
    public abstract ViewModel bindsHeadlineViewModel(HeadlineViewModel headlineViewModel);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(NewsViewModelFactory factory);
}