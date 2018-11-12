package com.example.sridharjajoo.newsapp.Utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            view.clearFocus();
            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
}
