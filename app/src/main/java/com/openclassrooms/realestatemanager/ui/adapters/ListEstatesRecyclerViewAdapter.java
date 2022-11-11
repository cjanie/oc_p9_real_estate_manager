package com.openclassrooms.realestatemanager.ui.adapters;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.ui.Action;
import com.openclassrooms.realestatemanager.ui.utils.Utils;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListEstatesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY_VIEW = 0;

    private static final int TYPE_ITEM_VIEW = 1;

    private SharedViewModel sharedViewModel;

    private List<Estate> estates;

    private boolean isDeviseEuro;

    public ListEstatesRecyclerViewAdapter(SharedViewModel sharedViewModel, boolean isDeviseEuro) {
        this.sharedViewModel = sharedViewModel;
        this.estates = new ArrayList<>();
        this.isDeviseEuro = isDeviseEuro;
    }

    public void updateList(List<Estate> estates) {
        this.estates.clear();
        this.estates.addAll(estates);

        // Notifies the recycler view that data changed
        // Notify or DiffUtil object to handle list changes
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_ITEM_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_estate_list_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_empty, parent, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder) {
            Estate estate = this.estates.get(position);
            ((ItemViewHolder) holder).type.setText(estate.getType().toString());
            ((ItemViewHolder) holder).location.setText(estate.getLocation());

            StringBuilder sb;
            if(this.isDeviseEuro) {
                sb = new StringBuilder("€ ");
                if(estate.getDevise().equals(Devise.DOLLAR)) {
                    int priceInEuro = Utils.convertDollarToEuro(Math.round(estate.getPrice()));
                    sb.append(priceInEuro);
                    ((ItemViewHolder) holder).price.setText(sb.append(priceInEuro).toString());
                } else {
                    int priceInEuro = Math.round(estate.getPrice());
                    ((ItemViewHolder) holder).price.setText(sb.append(priceInEuro).toString());
                }
            } else {
                sb = new StringBuilder("$ ");
                if(estate.getDevise().equals(Devise.EURO)) {
                    int priceInDollar = Utils.convertEuroToDollar(Math.round(estate.getPrice()));
                    sb.append(priceInDollar);
                } else {
                    int priceInDollar = Math.round(estate.getPrice());
                    sb.append(priceInDollar);
                }
            }

            ((ItemViewHolder) holder).price.setText(sb.toString());

            if(estate.getMedia() != null && !estate.getMedia().isEmpty()) {
                ((ItemViewHolder) holder).photo.setImageBitmap(BitmapFactory.decodeFile(estate.getMedia().get(0)));
            }

            boolean isTablet = holder.itemView.getContext().getResources().getBoolean(R.bool.is_tablet);
            if(isTablet) {
                sharedViewModel.getEstateSelectionId().observe((LifecycleOwner) holder.itemView.getContext(), estateSelectionId -> {
                    if(estateSelectionId != estate.getId()) {
                        holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.white));
                    }
                });
            }


            holder.itemView.setOnClickListener(view -> {
                // View Model Action
                sharedViewModel.updateAction(Action.DETAILS);
                sharedViewModel.updateEstateSelection(estate.getId());
                if(isTablet) {
                    view.setBackgroundColor(holder.itemView.getResources().getColor(R.color.selection_item));
                }
            });

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(this.estates.isEmpty() && position == 0) {
            return TYPE_EMPTY_VIEW;
        } else {
            return TYPE_ITEM_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        if(this.estates.isEmpty()) {
            return 1;
        }
        return this.estates.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView type;

        private TextView location;

        private TextView price;

        private ImageView photo;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.type = itemView.findViewById(R.id.estate_list_item_type);
            this.location = itemView.findViewById(R.id.estate_list_item_location);
            this.price = itemView.findViewById(R.id.estate_list_item_price);
            this.photo = itemView.findViewById(R.id.estate_list_item_photo);
        }

    }

}