package com.example.sridharjajoo.newsapp.data.Headline;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "articles")
public class Articles {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "news_description")
    public String description;

    @ColumnInfo(name = "news_image_url")
    public String urlToImage;

    @ColumnInfo(name = "news_title")
    public String title;

    @ColumnInfo(name = "news_date")
    public String publishedAt;

    @Embedded
    public Source source;
}
