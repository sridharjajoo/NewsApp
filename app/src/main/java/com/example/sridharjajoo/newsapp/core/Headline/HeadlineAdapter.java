package com.example.sridharjajoo.newsapp.core.Headline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.Utils.Utils;
import com.example.sridharjajoo.newsapp.data.AppDatabase;
import com.example.sridharjajoo.newsapp.data.Favourite.Favourite;
import com.example.sridharjajoo.newsapp.data.Headline.Articles;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class HeadlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final List<Articles> articlesList;
    private final Context context;
    private List<Articles> articles;
    private final AppDatabase db;

    public HeadlineAdapter(List<Articles> articlesList, FragmentActivity activity, AppDatabase db) {
        this.articlesList = articlesList;
        this.context = activity;
        this.db = db;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                View viewTop = layoutInflater.inflate(R.layout.item_headline_top, parent, false);
                return new HeadlineViewHolderTop(viewTop);
            default:
                View view = layoutInflater.inflate(R.layout.item_headline, parent, false);
                return new HeadlineViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Articles currentItem = articlesList.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                HeadlineViewHolderTop viewHolderTop = (HeadlineViewHolderTop) holder;
                configureViewHolderTop(viewHolderTop, currentItem);
                break;
            default:
                HeadlineViewHolder viewHolder = (HeadlineViewHolder) holder;
                configureViewHolder(viewHolder, currentItem);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void configureViewHolderTop(HeadlineViewHolderTop viewHolderTop, Articles currentItem) {
        viewHolderTop.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("pos", viewHolderTop.getAdapterPosition() + 1);
            intent.putExtra("title", currentItem.title);
            context.startActivity(intent);
        });
        viewHolderTop.headlineTitle.setText(currentItem.title);
        viewHolderTop.cardView.setElevation(0);
        Glide.with(context)
                .asBitmap()
                .load(currentItem.urlToImage)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        viewHolderTop.kenBurnsView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_insert_photo_black_24dp));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolderTop.kenBurnsView.setImageBitmap(resource);
                        return true;
                    }
                }).submit();
    }

    private void configureViewHolder(HeadlineViewHolder viewHolder, Articles currentItem) {
        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("pos", viewHolder.getAdapterPosition() + 1);
            intent.putExtra("title", currentItem.title);
            context.startActivity(intent);
        });
        Favourite favourite = new Favourite();
        favourite.articles = currentItem;
        int count = db.favoriteDao().isPresent(currentItem.title);
        viewHolder.favouriteCheckbox.setChecked(count == 1);
        viewHolder.favouriteCheckbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
                db.favoriteDao().insertFavourite(favourite);
            else
                db.favoriteDao().unfavouriteNews(currentItem.title);
        });
        viewHolder.description.setText(currentItem.title);
        viewHolder.newsSource.setText(currentItem.source.name);
        viewHolder.shareButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, currentItem.url);
            context.startActivity(Intent.createChooser(intent, "Share News"));
            Utils.shareNews(context, currentItem.url);
        });
        if (!currentItem.source.name.equals("Google News (India)"))
            viewHolder.newsTime.setText(Utils.formattedDate(currentItem.publishedAt));

        viewHolder.cardView.setElevation(0);

        Glide.with(context)
                .asBitmap()
                .load(currentItem.urlToImage)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(50, 10, RoundedCornersTransformation.CornerType.ALL)))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        viewHolder.newsImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_insert_photo_black_24dp));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.newsImage.setImageBitmap(resource);
                        return true;
                    }
                }).submit();
    }

    public void setArticlesList(final List<Articles> newList) {
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
