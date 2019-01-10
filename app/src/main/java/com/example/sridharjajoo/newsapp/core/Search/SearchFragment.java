package com.example.sridharjajoo.newsapp.core.Search;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.Utils.Utils;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.databinding.FragmentSearchBinding;
import com.example.sridharjajoo.newsapp.di.Injectable;

import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends Fragment implements Injectable {

    @Inject
    HeadlineService headlineService;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SearchAdapter searchAdapter;
    private AppDatabase db;
    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);
        db = AppDatabase.getAppDatabase(getActivity());
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        return binding.getRoot();
    }

    private void setRecyclerView(List<Articles> articlesList) {
        binding.cutomSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(articlesList, getActivity(), db);
        binding.cutomSearchRecycler.setAdapter(searchAdapter);
        loadArticles(articlesList);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        binding.cutomSearchRecycler.addItemDecoration(itemDecorator);
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
        searchViewModel.getProgress().observe(this, binding.progressBar::setVisibility);
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
                return true;
            }
        });

        SearchView searchView = new SearchView(getActivity());
        searchView.setBackgroundColor(Color.WHITE);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (Utils.hasNetwork()) {
                    handleSearch(query);
                    Utils.hideKeyboard(getView());
                } else {
                    Toast.makeText(getActivity(), "Network not available!", Toast.LENGTH_SHORT).show();
                }
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
        binding.noSearch.setVisibility(View.INVISIBLE);
        searchViewModel.customSearch(query).observe(this, this::showResult);
    }

    private void showResult(List<Articles> articles) {
        if (articles == null || (articles.size()==0)) {
            searchAdapter.clearRecyclerView();
            binding.noSearch.setVisibility(View.VISIBLE);
        }else {
            setRecyclerView(articles);
            binding.noSearch.setVisibility(View.INVISIBLE);
        }
    }
}
