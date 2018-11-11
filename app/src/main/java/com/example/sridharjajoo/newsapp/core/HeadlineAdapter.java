package com.example.sridharjajoo.newsapp.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineViewHolder> {

    private final List<Articles> articlesList;
    private List<Articles> articles;
    private Context context;

    public HeadlineAdapter(List<Articles> articlesList, FragmentActivity activity) {
        this.articlesList = articlesList;
        this.context = activity;
    }

    @NonNull
    @Override
    public HeadlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_headline, parent, false);
        return new HeadlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlineViewHolder holder, int position) {
        Articles currentItem = articlesList.get(position);
        holder.description.setText(currentItem.title);
        Glide.with(context)
                .load(currentItem.urlToImage)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(50, 10, RoundedCornersTransformation.CornerType.ALL)))
                .into(holder.newsImage);
    }

    protected void setArticlesList(final List<Articles> newList) {
        if (articles == null) {
            articles = newList;
            notifyItemRangeInserted(0, newList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return articles.size();
                }

                @Override
                public int getNewListSize() {
                    return newList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return articles.get(oldItemPosition).urlToImage
                            .equals(newList.get(newItemPosition).urlToImage);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return articles.get(oldItemPosition)
                            .equals(newList.get(newItemPosition));
                }
            });
            articles = newList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemCount() {
        if (articles == null)
            return 0;
        return articles.size();
    }
}
