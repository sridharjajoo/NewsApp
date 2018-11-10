package com.example.sridharjajoo.newsapp.data.Headline;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HeadlineApi {

    @GET("top-headlines?country=in&apiKey=aaefc3faa210424e9978fa75586d9580")
    Observable<HeadlineResponse> getHeadlines();
}
