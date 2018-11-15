package com.example.sridharjajoo.newsapp.core.Headline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sridharjajoo.newsapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

public class HeadlineViewHolderTop extends RecyclerView.ViewHolder {

    public TextView headlineTitle;
    public KenBurnsView kenBurnsView;
    public CardView cardView;

    public HeadlineViewHolderTop(View itemView) {
        super(itemView);
        headlineTitle = (TextView) itemView.findViewById(R.id.headline_title_top);
        kenBurnsView = (KenBurnsView) itemView.findViewById(R.id.kenburns_view);
        cardView = (CardView) itemView.findViewById(R.id.item_headline_top_card);
    }
}
