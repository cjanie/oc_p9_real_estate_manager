package com.openclassrooms.realestatemanager.ui.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Bitmap> bitmaps;

    public PhotosRecyclerViewAdapter(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_media_list_item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            Bitmap bitmap = this.bitmaps.get(position);
            ((ItemViewHolder) holder).media.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return this.bitmaps.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView media;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.media = itemView.findViewById(R.id.imageView_media);
        }
    }
}
