package com.example.sridharjajoo.newsapp.core.Headline;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.example.sridharjajoo.newsapp.NewsMainApplication;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class HeadlineViewModel extends ViewModel {

    private final HeadlineService headlineService;
    private List<Articles> articlesList;
    private final MutableLiveData<Integer> progress = new MutableLiveData<>();
    private final MutableLiveData<List<Articles>> articles = new MutableLiveData<>();

    @Inject
    public HeadlineViewModel(HeadlineService headlineService) {
        this.headlineService = headlineService;
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Articles>> getNewsArticles() {
        headlineService.getHeadline("in")
                .doOnSubscribe(disposable -> progress.setValue(0))
                .doFinally(() -> progress.setValue(8))
                .subscribe(status -> {
                    articles.setValue(status.articles);
                }, error -> {
                    Log.i("HeadlineFragment.class", "onStart: " + error);
                });
        return articles;
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }
}
