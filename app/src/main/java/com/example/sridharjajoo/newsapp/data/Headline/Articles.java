package com.example.sridharjajoo.newsapp.data.Headline;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Articles {
    public String description;
    public String urlToImage;
    public String url;
    public String title;
}
