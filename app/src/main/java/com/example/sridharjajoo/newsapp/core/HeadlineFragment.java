package com.example.sridharjajoo.newsapp.core;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.di.Injectable;

import javax.inject.Inject;

public class HeadlineFragment extends Fragment implements Injectable {

    @Inject
    HeadlineService headlineService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        Log.i("HeadlineFragment.class", "onCreateView: " + headlineService);
        headlineService.getHeadline("in")
                .doOnSubscribe(disposable -> {})
                .doFinally(() -> {})
                .subscribe(status -> {
                    Log.i("HeadlineFragment.class", "Ans: " + status.status + " " + status.totalResults + " " + status.articles.get(0).description);
                });
    }
}
