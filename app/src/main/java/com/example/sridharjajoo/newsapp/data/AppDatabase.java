package com.example.sridharjajoo.newsapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.NewsDao;

import javax.inject.Singleton;

@Database(entities = {Articles.class}, version = 1)
//@TypeConverters({SourceConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

    public static AppDatabase getAppDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "news-database")
            .allowMainThreadQueries()
            .build();
    }
}
