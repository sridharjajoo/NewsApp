package com.example.sridharjajoo.newsapp.core.Headline;

import io.reactivex.annotations.NonNull;

public interface IHeadlineDetail {

    @NonNull
    void imageUrl(String image);

    @NonNull
    void articleDescription(String description);
}
