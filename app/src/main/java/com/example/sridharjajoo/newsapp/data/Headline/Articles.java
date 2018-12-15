package com.example.sridharjajoo.newsapp.data.Headline;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Data;

@Data
@Entity(tableName = "articles")
public class Articles {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Expose(serialize = false, deserialize = false)
    public long idArticle;

    @ColumnInfo(name = "news_description")
    public String description;

    @ColumnInfo(name = "news_content")
    public String content;

    @ColumnInfo(name = "news_image_url")
    public String urlToImage;

    @ColumnInfo(name = "news_title")
    public String title;

    @ColumnInfo(name = "news_date")
    public String publishedAt;

    @ColumnInfo(name = "news_url")
    public String url;

    @Embedded
    public Source source;
}
