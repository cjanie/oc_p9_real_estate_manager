package com.openclassrooms.realestatemanager.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;

import java.util.ArrayList;
import java.util.List;

public abstract class MediaRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final int TYPE_EMPTY_VIEW = 0;

    protected final int TYPE_ITEM_VIEW = 1;

    protected List<Media> mediaList;

    public MediaRecyclerViewAdapter() {
        this.mediaList = new ArrayList<>();
    }

    public void updateList(List<Media> mediaList) {
        this.mediaList.clear();
        this.mediaList.addAll(mediaList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(this.mediaList.isEmpty()) {
            return 1;
        }
        return this.mediaList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(this.mediaList.isEmpty() && position == 0) {
            return TYPE_EMPTY_VIEW;
        } else {
            return TYPE_ITEM_VIEW;
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
