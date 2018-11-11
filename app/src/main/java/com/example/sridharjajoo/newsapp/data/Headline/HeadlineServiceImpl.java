package com.example.sridharjajoo.newsapp.data.Headline;


import com.example.sridharjajoo.newsapp.data.CustomSearch.CustomSearchResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

public class HeadlineServiceImpl implements HeadlineService {

    private final HeadlineApi headlineApi;

    @Inject
    public HeadlineServiceImpl(HeadlineApi headlineApi) {
        this.headlineApi = headlineApi;
    }

    @Override
    public Observable<HeadlineResponse> getHeadline(String headlineRequest) {
        return headlineApi.getHeadlines()
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread());
    }

    @Override
    public Observable<CustomSearchResponse> getCustomSearchReponse(String query) {
        return headlineApi.getSearchResponse(query, "aaefc3faa210424e9978fa75586d9580")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread());
    }
}
