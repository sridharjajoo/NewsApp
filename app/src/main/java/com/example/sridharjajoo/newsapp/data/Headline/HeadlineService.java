package com.example.sridharjajoo.newsapp.data.Headline;

import com.example.sridharjajoo.newsapp.data.CustomSearch.CustomSearchResponse;
import com.example.sridharjajoo.newsapp.data.Settings.SettingsResponse;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.Response;

public interface HeadlineService {

    @NonNull
    Observable<HeadlineResponse> getHeadline(String headlineRequest);

    @NonNull
    Observable<CustomSearchResponse> getCustomSearchReponse(String query);

    @NonNull
    Observable<SettingsResponse> getSources();
}
