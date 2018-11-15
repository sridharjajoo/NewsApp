package com.example.sridharjajoo.newsapp.data.Headline;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sridharjajoo.newsapp.Utils.Utils;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.CustomSearch.CustomSearchResponse;
import com.example.sridharjajoo.newsapp.data.Settings.SettingsResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

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
        List<String> list = sharedPreferenceHandle();
        String csvString = Utils.getCSVString(list);
        if (csvString.isEmpty()) {
            return headlineApi.getHeadlines("in", Utils.apiKey)
                    .doOnNext(this::syncSaveHeadlines)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThread());
        } else {
            return headlineApi.getHeadlinesFiltered(csvString, Utils.apiKey)
                    .doOnNext(this::syncSaveHeadlines)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThread());
        }
    }

    @Override
    public Observable<CustomSearchResponse> getCustomSearchReponse(String query) {
        return headlineApi.getSearchResponse(query, Utils.apiKey)
                .doOnNext(this::syncSaveCustom)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread());
    }

    private void syncSaveCustom(CustomSearchResponse response) {
        appDatabase = AppDatabase.getAppDatabase(context);
        List<Articles> articles = response.articles;
        for (Articles newsArticle : articles) {
            appDatabase.newsDao().insertAt(newsArticle);
        }
    }

    @Override
    public Observable<SettingsResponse> getSources() {
        return headlineApi.getSourcesResponse(Utils.apiKey, "in")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread());
    }

    //store the articles list to DB
    public void syncSaveHeadlines(HeadlineResponse headlineResponse) {
        appDatabase = AppDatabase.getAppDatabase(context);
        List<Articles> articles = headlineResponse.articles;
        for (Articles newsArticle : articles) {
            appDatabase.newsDao().insertAt(newsArticle);
        }
    }

    private List<String> sharedPreferenceHandle() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean times = sharedPreferences.getBoolean("time", false);
        boolean hindu = sharedPreferences.getBoolean("hindu", false);
        boolean google = sharedPreferences.getBoolean("google", false);
        String query = times ? "the-times-of-india" : "";
        String query2 = hindu ? "the-hindu" : "";
        String query3 = google ? "google-news-in" : "";
        List<String> list = new ArrayList<>();
        list.add(query);
        list.add(query2);
        list.add(query3);
        return list;
    }
}
