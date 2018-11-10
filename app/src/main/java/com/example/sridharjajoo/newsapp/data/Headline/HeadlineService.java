package com.example.sridharjajoo.newsapp.data.Headline;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface HeadlineService {

    @NonNull
    Observable<HeadlineResponse> getHeadline(String headlineRequest);
}
