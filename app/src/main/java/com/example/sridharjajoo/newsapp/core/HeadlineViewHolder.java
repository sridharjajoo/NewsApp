package com.example.sridharjajoo.newsapp.core;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sridharjajoo.newsapp.R;

public class HeadlineViewHolder extends RecyclerView.ViewHolder {

    public TextView description;
    public ImageView newsImage;
    public CardView cardView;

    public HeadlineViewHolder(View itemView) {
        super(itemView);
        description = (TextView) itemView.findViewById(R.id.description);
        newsImage = (ImageView) itemView.findViewById(R.id.image_news);
        cardView = (CardView) itemView.findViewById(R.id.item_headline_card);
    }
}
