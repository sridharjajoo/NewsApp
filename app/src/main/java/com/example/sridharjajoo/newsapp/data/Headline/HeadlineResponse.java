package com.example.sridharjajoo.newsapp.data.Headline;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class HeadlineResponse {

    public long totalResults;
    public String status;
    public List<Articles> articles;
}
