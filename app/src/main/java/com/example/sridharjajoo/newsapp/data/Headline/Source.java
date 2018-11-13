package com.example.sridharjajoo.newsapp.data.Headline;


import android.arch.persistence.room.ColumnInfo;

import lombok.Data;

@Data
public class Source {
    @ColumnInfo(name = "source_name")
    public String name;

    @ColumnInfo(name = "source_id")
    public String id;
}
