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

    @Query("SELECT * FROM articles WHERE news_title = :currentTitle")
    Articles getArticleString(String currentTitle);

    @Query("SELECT COUNT(*) FROM articles")
    int getCount();

    @Query("DELETE FROM articles")
    void deleteTable();

    @Insert
    void insertAt(Articles... articles);
}
