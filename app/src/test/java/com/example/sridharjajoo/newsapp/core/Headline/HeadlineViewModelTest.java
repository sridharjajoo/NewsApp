package com.example.sridharjajoo.newsapp.core.Headline;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.sridharjajoo.newsapp.data.Headline.Articles;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineResponse;
import com.example.sridharjajoo.newsapp.data.Headline.HeadlineService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;


@RunWith(JUnit4.class)
public class HeadlineViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    HeadlineService headlineService;
    @Mock
    android.arch.lifecycle.Observer<Boolean> progress;

    private HeadlineViewModel headlineViewModel;

    @Before
    public void setUp() {
        headlineViewModel = new HeadlineViewModel(headlineService);
    }

    @Test
    public void shoudlLoadArticlesSUccessfully() {
        //TODO
    }
}
