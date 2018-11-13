package com.example.sridharjajoo.newsapp.data.Favourite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.sridharjajoo.newsapp.data.Headline.Articles;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favourites")
    List<Favourite> getFavourites();

    @Insert
    void insertFavourite(Favourite... favourites);
}
