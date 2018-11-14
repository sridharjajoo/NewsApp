package com.example.sridharjajoo.newsapp.core.Setttings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.sridharjajoo.newsapp.R;
import com.example.sridharjajoo.newsapp.data.Settings.SourceSettings;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsViewHolder>{

    private List<SourceSettings> sourceSettingsList;
    private Context context;

    public SettingsAdapter(List<SourceSettings> sourcesList, SettingsActivity settingsActivity) {
        this.sourceSettingsList = sourcesList;
        this.context = settingsActivity;
    }

    public void setSourceSettingsList(final List<SourceSettings> newList) {
        if (sourceSettingsList == null) {
            sourceSettingsList = newList;
            notifyItemRangeInserted(0, newList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return sourceSettingsList.size();
                }

                @Override
                public int getNewListSize() {
                    return newList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return sourceSettingsList.get(oldItemPosition).id
                            .equals(newList.get(newItemPosition).id);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return sourceSettingsList.get(oldItemPosition)
                            .equals(newList.get(newItemPosition));
                }
            });
            sourceSettingsList = newList;
            result.dispatchUpdatesTo(this);
        }

    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_settings, parent, false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        SourceSettings sourceSettings = sourceSettingsList.get(position);
        holder.title.setText(sourceSettings.name);
        holder.category.setText(sourceSettings.category);
    }

    @Override
    public int getItemCount() {
        if (sourceSettingsList == null)
            return 0;
        return sourceSettingsList.size();
    }
}