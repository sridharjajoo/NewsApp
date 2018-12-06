package com.example.sridharjajoo.newsapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.sridharjajoo.newsapp.NewsMainApplication;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class Utils {

   public static String apiKey = "aaefc3faa210424e9978fa75586d9580";

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            view.clearFocus();
            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
   
   public static void showKeyboard(View view){
        if(view != null){
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        }
    }


    public static String formattedDate(String dateUTC) {
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date sd = null;
        try {
            sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateUTC);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(sd);
        return formattedDate;
    }

    public static String getCSVString(List<String> sourcesList) {
        StringBuilder csvBuilder = new StringBuilder();
        for (String newsPaper : sourcesList) {
            if (!newsPaper.isEmpty()) {
                csvBuilder.append(newsPaper);
                csvBuilder.append(",");
            }
        }
        return csvBuilder.toString();
    }

    public static String truncateExtra(String content) {
        if (content == null)
            return "";
        return content.replaceAll("(\\[\\+\\d+ chars])", "");
    }

    public static boolean hasNetwork() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) NewsMainApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String getCountry() {
        String country_name = null;
        LocationManager lm = (LocationManager)(NewsMainApplication.context).getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(NewsMainApplication.context);
        for(String provider: lm.getAllProviders()) {
            @SuppressWarnings("ResourceType") Location location = lm.getLastKnownLocation(provider);
            if(location!=null) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if(addresses != null && addresses.size() > 0) {
                        country_name = addresses.get(0).getCountryName();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return country_name;
    }
}
