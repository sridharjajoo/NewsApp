package com.example.sridharjajoo.newsapp.core.Setttings;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sridharjajoo.newsapp.R;

public class SettingsViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;
    public TextView category;
    public ImageView image;

    public SettingsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.tv_source_name);
        description = (TextView) itemView.findViewById(R.id.tv_source_desc);
        category = (TextView) itemView.findViewById(R.id.tv_source_category);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
