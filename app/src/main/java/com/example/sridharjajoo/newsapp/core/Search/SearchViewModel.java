package com.example.sridharjajoo.newsapp.core.Search;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private final HeadlineService headlineService;
    private MutableLiveData<Integer> progress = new MutableLiveData<>();
    private MutableLiveData<List<Articles>> articles = new MutableLiveData<>();

    @Inject
    public SearchViewModel(HeadlineService headlineService) {
        this.headlineService = headlineService;
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Articles>> customSearch(String s) {
        headlineService.getCustomSearchReponse(s)
                .doOnSubscribe(disposable -> progress.setValue(View.VISIBLE))
                .doFinally(() -> progress.setValue(View.GONE))
                .subscribe(response -> {
                    articles.setValue(response.articles);
                }
                , error -> Log.i("SearchViewModel.class", "customSearch: " + error));
        return articles;
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }
}
