package com.example.sridharjajoo.newsapp.data.Headline;


import android.content.Context;
import android.util.Log;

import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.CustomSearch.CustomSearchResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class HeadlineServiceImpl implements HeadlineService {

    private final HeadlineApi headlineApi;
    private final Context context;
    private AppDatabase appDatabase;

    @Inject
    public HeadlineServiceImpl(HeadlineApi headlineApi, Context context) {
        this.headlineApi = headlineApi;
        this.context = context;
    }

    @Override
    public Observable<HeadlineResponse> getHeadline(String headlineRequest) {
        return headlineApi.getHeadlines()
                .doOnNext(this::syncSave)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread());
    }

    @Override
    public Observable<CustomSearchResponse> getCustomSearchReponse(String query) {
        return headlineApi.getSearchResponse(query, "aaefc3faa210424e9978fa75586d9580")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread());
    }

    //store the articles list to DB
    public void syncSave(HeadlineResponse headlineResponse) {
        appDatabase = AppDatabase.getAppDatabase(context);
        List<Articles> articles = headlineResponse.articles;
        int i = 0;
        for (Articles newsArticle: articles) {
            appDatabase.newsDao().insertAt(newsArticle);
            Log.i("HeadlineService", "syncSave: " + appDatabase.newsDao().newsArticles().get(i) + "\n");
            i++;
        }
    }
}
