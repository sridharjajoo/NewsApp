package com.example.sridharjajoo.newsapp.data.Settings;

import java.util.List;

import lombok.Data;

@Data
public class SettingsResponse {

    public String status;
    public List<SourceSettings> sources;
}
