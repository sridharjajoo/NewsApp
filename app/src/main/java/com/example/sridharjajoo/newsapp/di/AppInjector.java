package com.example.sridharjajoo.newsapp.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.sridharjajoo.newsapp.NewsMainApplication;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public final class AppInjector {

    //empty constructor
    public AppInjector() {

    }

    public static void init(NewsMainApplication newsMainApplication) {
        DaggerAppComponent
                .create()
                .inject(newsMainApplication);

        newsMainApplication
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                        // Do Nothing
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        // Do Nothing
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                        // Do Nothing
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                        // Do Nothing
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                        // Do Nothing
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        // Do Nothing
                    }
                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasSupportFragmentInjector) {
            AndroidInjection.inject(activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(FragmentManager fm, Fragment f,
                                                              Bundle savedInstanceState) {
                                    if (f instanceof Injectable) {
                                        AndroidSupportInjection.inject(f);
                                    }
                                }
                            }, true);
        }
    }
}
