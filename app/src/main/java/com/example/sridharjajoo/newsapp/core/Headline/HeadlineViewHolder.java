package com.example.sridharjajoo.newsapp.core.Headline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sridharjajoo.newsapp.R;

public class HeadlineViewHolder extends RecyclerView.ViewHolder {

    public TextView description;
    public ImageView newsImage;
    public CardView cardView;
    public TextView newsTime;
    public TextView newsSource;
    public CheckBox favouriteCheckbox;
    public ImageButton shareButton;

    public HeadlineViewHolder(View itemView) {
        super(itemView);
        description = (TextView) itemView.findViewById(R.id.description);
        newsImage = (ImageView) itemView.findViewById(R.id.image_news);
        cardView = (CardView) itemView.findViewById(R.id.item_headline_card);
        newsTime = (TextView) itemView.findViewById(R.id.news_time);
        newsSource = (TextView) itemView.findViewById(R.id.news_source);
        favouriteCheckbox = (CheckBox) itemView.findViewById(R.id.favourite_icon);
        shareButton = (ImageButton) itemView.findViewById(R.id.share_button);
    }

}
