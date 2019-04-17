package com.example.sridharjajoo.newsapp.core.Favourite;

import android.arch.persistence.room.RoomDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineAdapter;
import com.example.sridharjajoo.newsapp.core.Search.SearchAdapter;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.Favourite.Favourite;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.Source;
import com.example.sridharjajoo.newsapp.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import dagger.Binds;

public class FavouriteFragment extends Fragment {

    private AppDatabase db;
    private SearchAdapter searchAdapter;
    private List<Articles> listArticles = new ArrayList<>();
    private FragmentFavouriteBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false);
        getSavedNews();
        return binding.getRoot();
    }


    private void setRecyclerView(List<Articles> articlesList) {
        binding.favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(articlesList, getActivity(), db);
        binding.favouriteRecyclerView.setAdapter(searchAdapter);
        loadArticles(articlesList);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider)));
        binding.favouriteRecyclerView.addItemDecoration(itemDecorator);
    }

    private void loadArticles(List<Articles> articlesList) {
        searchAdapter.setArticlesList(articlesList);
    }

    private void getSavedNews(){
        db = AppDatabase.getAppDatabase(getActivity());
        List<Favourite> favouriteList = db.favoriteDao().getFavourites();
        for (int i = 0; i < favouriteList.size(); i++) {
            listArticles.add(favouriteList.get(i).articles);
        }
        setRecyclerView(listArticles);
    }

}
