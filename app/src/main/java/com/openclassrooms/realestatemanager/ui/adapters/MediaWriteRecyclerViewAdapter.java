package com.openclassrooms.realestatemanager.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;

public class MediaWriteRecyclerViewAdapter extends MediaRecyclerViewAdapter {

    private UpdateMedia updateMedia;

    public MediaWriteRecyclerViewAdapter(UpdateMedia updateMedia) {
        this.updateMedia = updateMedia;
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
            Media media = this.mediaList.get(position);
            Bitmap bitmap = BitmapFactory.decodeFile(media.getPath());
            ((ItemViewHolder) holder).media.setImageBitmap(bitmap);
            if(media.getName() != null) {
                ((ItemViewHolder) holder).name.setText(media.getName());
            }

            ((ItemViewHolder) holder).name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    media.setName(charSequence.toString());
                    updateMedia.updateMedia(media);
                    updateMedia.enableSaveButton();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView media;

        private EditText name;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.media = itemView.findViewById(R.id.imageView_media);
            this.name = itemView.findViewById(R.id.editText_media);
            this.name.setVisibility(View.VISIBLE);
        }
    }

    public interface UpdateMedia {
        void enableSaveButton();
        void updateMedia(Media media);
    }
}
