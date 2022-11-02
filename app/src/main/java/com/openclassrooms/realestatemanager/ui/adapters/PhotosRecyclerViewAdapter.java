package com.openclassrooms.realestatemanager.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_EMPTY_VIEW = 0;

    private static final int TYPE_ITEM_VIEW = 1;

    private List<Bitmap> photos;

    public PhotosRecyclerViewAdapter() {
        this.photos = new ArrayList<>();
    }

    public void updateList(List<Bitmap> photos) {
        this.photos.clear();
        this.photos.addAll(photos);

        // Notifies the recycler view that data changed
        // Notify or DiffUtil object to handle list changes
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_media_list_item, parent, false);

        if(viewType == TYPE_ITEM_VIEW) {
            return new ItemViewHolder(view);

        } else {
            return new EmptyViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            Bitmap photo = this.photos.get(position);
            ((ItemViewHolder) holder).media.setImageBitmap(photo);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(this.photos.isEmpty() && position == 0) {
            return TYPE_EMPTY_VIEW;
        } else {
            return TYPE_ITEM_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        if(this.photos.isEmpty()) {
            return 1;
        }
        return this.photos.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView media;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.media = itemView.findViewById(R.id.imageView_media);
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
