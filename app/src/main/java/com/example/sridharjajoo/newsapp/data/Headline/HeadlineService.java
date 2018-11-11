package com.example.sridharjajoo.newsapp.data.Headline;

import com.example.sridharjajoo.newsapp.data.CustomSearch.CustomSearchResponse;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface HeadlineService {

    @NonNull
    Observable<HeadlineResponse> getHeadline(String headlineRequest);

    @NonNull
    Observable<CustomSearchResponse> getCustomSearchReponse(String query);
}
