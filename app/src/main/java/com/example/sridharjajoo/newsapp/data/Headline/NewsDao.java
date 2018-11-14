package com.example.sridharjajoo.newsapp.data.Headline;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM articles")
    List<Articles> newsArticles();

    @Query("SELECT * FROM articles WHERE id = :pos ")
    Articles getArticle(int pos);

    @Query("DELETE FROM articles")
    void deleteTable();

    @Insert
    void insertAt(Articles... articles);
}
