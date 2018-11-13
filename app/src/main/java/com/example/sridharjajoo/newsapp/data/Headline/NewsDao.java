package com.example.sridharjajoo.newsapp.data.Headline;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM articles")
    List<Articles> newsArticles();

    @Insert
    void insertAt(Articles... articles);
}
