package com.openclassrooms.realestatemanager.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.PhotosRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EstateMediaFragment extends BaseFragment {

    private RecyclerView photosRecyclerView;
    private PhotosRecyclerViewAdapter photosRecyclerViewAdapter;

    private HandlePhotos handlePhotos;

    public EstateMediaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_estate_media, container, false);
        this.photosRecyclerView = root.findViewById(R.id.recyclerView_media);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        this.photosRecyclerView.setLayoutManager(horizontalLayoutManager);

        this.photosRecyclerViewAdapter = new PhotosRecyclerViewAdapter();
        this.photosRecyclerView.setAdapter(this.photosRecyclerViewAdapter);

        List<Bitmap> bitmaps = this.handlePhotos.getPhotos();
        this.photosRecyclerViewAdapter.updateList(bitmaps);

        return root;
    }

    interface HandlePhotos {
        List<Bitmap> getPhotos();
    }
}
