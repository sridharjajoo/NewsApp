package com.example.sridharjajoo.newsapp.core.Headline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.di.Injectable;

public class HeadlineDetail extends Fragment implements Injectable {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_headline_detail, container, false);
        return view;
    }
}
