package com.example.sridharjajoo.newsapp.core.Setttings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.core.Headline.HeadlineAdapter;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.data.Settings.SourceSettings;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SettingsActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    HeadlineService headlineService;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @BindView(R.id.news_source_rv)
    RecyclerView sourceRecyclerView;

    @BindView(R.id.times_of_india)
    CheckBox timesOfIndia_cb;

    @BindView(R.id.the_hindu)
    CheckBox theHindu_cb;

    @BindView(R.id.google_news_india)
    CheckBox googleNewsIndia_cb;

    private List<SourceSettings> sourcesList;
    private SettingsAdapter settingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        sharedPreference();
    }

    private void sharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        timesOfIndia_cb.setChecked(sharedPreferences.getBoolean("time", false));
        theHindu_cb.setChecked(sharedPreferences.getBoolean("hindu", false));
        googleNewsIndia_cb.setChecked(sharedPreferences.getBoolean("google", false));

        timesOfIndia_cb.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("time", b);
            editor.commit();
        });

        theHindu_cb.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("hindu", b);
            editor.commit();
        });

        googleNewsIndia_cb.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("google", b);
            editor.commit();
        });
    }

    private void loadSources() {
        headlineService.getSources()
                    .subscribe(settingsResponse -> {
                        this.sourcesList = settingsResponse.sources;
                        Log.i("SettingsActivity.class", "loadSources: " + sourcesList);
                        setUpRecyclerView(sourcesList);
                    });
    }

    private void setUpRecyclerView(List<SourceSettings> sourcesList) {
        sourceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingsAdapter = new SettingsAdapter(sourcesList, this);
        sourceRecyclerView.setAdapter(settingsAdapter);
        loadSourcesList(sourcesList);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        sourceRecyclerView.addItemDecoration(itemDecorator);
    }

    private void loadSourcesList(List<SourceSettings> sourcesList) {
        settingsAdapter.setSourceSettingsList(sourcesList);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
