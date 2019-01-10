package com.example.sridharjajoo.newsapp.core.Category;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.Utils.Utils;
import com.example.sridharjajoo.newsapp.core.Search.SearchAdapter;
import com.example.sridharjajoo.newsapp.core.Search.SearchViewModel;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.databinding.FragmentCategoryBinding;
import com.example.sridharjajoo.newsapp.di.Injectable;

import java.util.List;

import javax.inject.Inject;


public class CategoryFragment extends Fragment implements Injectable {

    @Inject
    HeadlineService headlineService;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentCategoryBinding binding;
    private CategoryViewModel categoryViewModel;
    private SearchAdapter searchAdapter;
    private AppDatabase db;
    private String category;
    private int selectedCategory=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        db = AppDatabase.getAppDatabase(getActivity());
        categoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!Utils.hasNetwork()) {
            Toast.makeText(getActivity(), "Network not available!", Toast.LENGTH_SHORT).show();
        }
        categoryViewModel.getProgress().observe(this, binding.progressBar::setVisibility);
        selectedCategory = getArguments().getInt("search");
        category = findCategory(selectedCategory);
        showHeadlineByCategory(category);
    }

    private void setRecyclerView(List<Articles> articlesList) {
        binding.categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(articlesList, getActivity(), db);
        binding.categoryRecycler.setAdapter(searchAdapter);
        loadArticles(articlesList);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        binding.categoryRecycler.addItemDecoration(itemDecorator);
    }

    private void loadArticles(List<Articles> articlesList) {
        searchAdapter.setArticlesList(articlesList);
    }

    private void showHeadlineByCategory(String category){
        binding.noSearch.setVisibility(View.INVISIBLE);
        categoryViewModel.headlineByCategory(category).observe(this, this::showResult);
    }


    private String findCategory(int selectedCategory){
        switch(selectedCategory){
            case 0 : return  "science";
            case 1 : return  "sports";
            case 2 : return  "entertainment";
            default: return  "science";
        }
    }

    private void showResult(List<Articles> articles) {
        if (articles == null || (articles.size()==0)) {
            searchAdapter.clearRecyclerView();
            binding.noSearch.setVisibility(View.VISIBLE);
        } else{
            setRecyclerView(articles);
            binding.noSearch.setVisibility(View.INVISIBLE);
        }

    }

}
