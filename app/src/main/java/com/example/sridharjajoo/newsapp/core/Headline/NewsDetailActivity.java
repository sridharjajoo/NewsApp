package com.example.sridharjajoo.newsapp.core.Headline;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.Utils.Utils;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_news_image)
    ImageView newsDetailsImage;

    @BindView(R.id.tv_news_desc)
    TextView newsDetails;

    @BindView(R.id.tv_news_source)
    TextView newsSource;

    @BindView(R.id.tv_news_title)
    TextView newsTitle;

    @BindView(R.id.btn_read_full)
    Button newsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        if (bundle.getString("title") != null) {
            Articles currentArticle = AppDatabase.getAppDatabase(this).newsDao().getArticleString(bundle.getString("title"));
            newsDetails.setText(Utils.truncateExtra(currentArticle.content));
            Glide.with(this).load(currentArticle.urlToImage).into(newsDetailsImage);
            newsSource.setText(currentArticle.source.name);
            newsTitle.setText(currentArticle.title);

            newsButton.setOnClickListener(view -> {
                String url = currentArticle.url;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(url));
            });
        }
    }
}
