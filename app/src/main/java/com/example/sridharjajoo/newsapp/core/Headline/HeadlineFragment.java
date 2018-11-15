package com.example.sridharjajoo.newsapp.core.Headline;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.Utils.Utils;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.di.Injectable;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadlineFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    HeadlineService headlineService;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_headline)
    RecyclerView recyclerHeadline;

    @BindView(R.id.custom_search)
    SearchView searchView;

    private List<Articles> articlesList;
    private HeadlineAdapter headlineAdapter;
    private View view;
    private AppDatabase db;
    protected LocationManager locationManager;
    private HeadlineViewModel headlineViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_headline, container, false);
        ButterKnife.bind(this, view);
        db = AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()));
        headlineViewModel = ViewModelProviders.of(this, viewModelFactory).get(HeadlineViewModel.class);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        if (!Utils.hasNetwork()) {
            Toast.makeText(getActivity(), "Network not available!", Toast.LENGTH_SHORT).show();
        }
        headlineViewModel.getProgress().observe(this, progressBar::setVisibility);
        loadNewsArticles();
    }

    private void loadNewsArticles() {
        headlineViewModel.getNewsArticles().observe(this, list -> {
            articlesList = list;
            if (articlesList != null) {
                setRecyclerView(articlesList);
            }
        });
    }

    private void setRecyclerView(List<Articles> articlesList) {
        recyclerHeadline.setLayoutManager(new LinearLayoutManager(getContext()));
        headlineAdapter = new HeadlineAdapter(articlesList, getActivity(), db);
        recyclerHeadline.setAdapter(headlineAdapter);
        loadArticles(articlesList);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider)));
        recyclerHeadline.addItemDecoration(itemDecorator);
    }

    private void loadArticles(List<Articles> articlesList) {
        headlineAdapter.setArticlesList(articlesList);
    }
}
