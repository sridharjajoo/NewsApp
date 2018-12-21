package com.example.sridharjajoo.newsapp.core.Setttings;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;

import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;
import com.example.sridharjajoo.newsapp.data.Settings.SourceSettings;
import com.example.sridharjajoo.newsapp.databinding.ActivityNewsMainBinding;
import com.example.sridharjajoo.newsapp.databinding.ActivitySettingsBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SettingsActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    HeadlineService headlineService;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private List<SourceSettings> sourcesList;
    private ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_settings);
        sharedPreference();
    }

    private void sharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        binding.timesOfIndia.setChecked(sharedPreferences.getBoolean("time", false));
        binding.theHindu.setChecked(sharedPreferences.getBoolean("hindu", false));
        binding.googleNewsIndia.setChecked(sharedPreferences.getBoolean("google", false));

        binding.timesOfIndia.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("time", b);
            editor.commit();
        });

        binding.theHindu.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("hindu", b);
            editor.commit();
        });

        binding.googleNewsIndia.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean("google", b);
            editor.commit();
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
