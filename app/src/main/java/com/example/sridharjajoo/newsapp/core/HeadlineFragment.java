package com.example.sridharjajoo.newsapp.core;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.di.Injectable;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadlineFragment extends Fragment implements Injectable {

    @Inject
    HeadlineService headlineService;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_headline)
    RecyclerView recyclerHeadline;

    private List<Articles> articlesList;
    private HeadlineAdapter headlineAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headline, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        headlineService.getHeadline("us")
                .doOnSubscribe(disposable -> progressBar.setVisibility(View.VISIBLE))
                .doFinally(() -> progressBar.setVisibility(View.GONE))
                .subscribe(status -> {
                    this.articlesList = status.articles;
                    setRecyclerView(articlesList);
                });
    }

    private void setRecyclerView(List<Articles> articlesList) {
        recyclerHeadline.setLayoutManager(new LinearLayoutManager(getContext()));
        headlineAdapter = new HeadlineAdapter(articlesList, getActivity());
        recyclerHeadline.setAdapter(headlineAdapter);
        loadArticles(articlesList);
    }

    private void loadArticles(List<Articles> articlesList) {
        headlineAdapter.setArticlesList(articlesList);
    }
}
