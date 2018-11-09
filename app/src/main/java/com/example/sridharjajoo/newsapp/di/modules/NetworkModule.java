package com.example.sridharjajoo.newsapp.di.modules;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module(includes = ApiModule.class)
public class NetworkModule {

    @Provides
    @Singleton
    ObjectMapper providesObjectMapper() {
        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                 .setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    }

//    @Provides
//    Class[] providesMappedClasses() {
//        return new Class[]{ SearchResponse.class};
//    }

    @Provides
    @Singleton
    @Named("jsonapi")
    Converter.Factory providesJsonApiFactory(ObjectMapper objectMapper, Class... mappedClasses) {
        return new JSONAPIConverterFactory(objectMapper, mappedClasses);
    }

    @Provides
    @Singleton
    @Named("jackson")
    Converter.Factory providesJacksonFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Provides
    @Singleton
    CallAdapter.Factory providesCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofitBuilder(CallAdapter.Factory callAdapterFactory,
                                    OkHttpClient client, @Named("jackson") Converter.Factory factory) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(callAdapterFactory)
                .baseUrl("https://newsapi.org/v2/")
                .build();
    }
}
