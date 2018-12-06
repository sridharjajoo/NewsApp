package com.example.sridharjajoo.newsapp.core.Search;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.Utils.Utils;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineAdapter;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.di.Injectable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment implements Injectable {

    @Inject
    HeadlineService headlineService;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.cutom_search_recycler)
    RecyclerView customSearchRecycler;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SearchAdapter searchAdapter;
    private View view;
    private AppDatabase db;
    private SearchViewModel searchViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        db = AppDatabase.getAppDatabase(getActivity());
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        return view;
    }

    private void setRecyclerView(List<Articles> articlesList) {
        customSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(articlesList, getActivity(), db);
        customSearchRecycler.setAdapter(searchAdapter);
        loadArticles(articlesList);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        customSearchRecycler.addItemDecoration(itemDecorator);
    }

    private void loadArticles(List<Articles> articlesList) {
        searchAdapter.setArticlesList(articlesList);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!Utils.hasNetwork()) {
            Toast.makeText(getActivity(), "Network not available!", Toast.LENGTH_SHORT).show();
        }
        searchViewModel.getProgress().observe(this, progressBar::setVisibility);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_search_black_24dp);
        drawable.setTint(ContextCompat.getColor(getActivity(), R.color.white));
        item.setIcon(drawable);
        
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Utils.showKeyboard(getView());
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });

        SearchView searchView = new SearchView(getActivity());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        item.setActionView(searchView);
    }

    private void handleSearch(String query) {
        searchViewModel.customSearch(query).observe(this, this::showResult);
    }

    private void showResult(List<Articles> articles) {
        if (articles == null) {
            return;
        }
        setRecyclerView(articles);
    }
}
