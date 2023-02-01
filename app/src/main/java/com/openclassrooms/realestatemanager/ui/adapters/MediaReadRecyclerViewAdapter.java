package com.openclassrooms.realestatemanager.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;

public class MediaReadRecyclerViewAdapter extends MediaRecyclerViewAdapter {

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
            Media media = this.mediaList.get(position);
            Bitmap bitmap = BitmapFactory.decodeFile(media.getPath());
            ((ItemViewHolder) holder).media.setImageBitmap(bitmap);
            if(media.getName() != null) {
                ((ItemViewHolder) holder).name.setText(media.getName());
                ((ItemViewHolder) holder).name.setVisibility(View.VISIBLE);
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView media;

        private TextView name;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.media = itemView.findViewById(R.id.imageView_media);
            this.name = itemView.findViewById(R.id.textView_media);
        }
    }
}
