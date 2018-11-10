package com.example.sridharjajoo.newsapp.data.Headline;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HeadlineApi {

    @GET("/top-headlines")
    Observable<HeadlineResponse> getHeadlines(@Query("country") String countryCode,
                                              @Query("apiKey") String key);
}
