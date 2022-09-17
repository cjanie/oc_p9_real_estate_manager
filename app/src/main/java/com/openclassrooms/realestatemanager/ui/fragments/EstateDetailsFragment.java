package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;

public class EstateDetailsFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private TextView surface;
    private TextView numberOfRooms;
    private TextView numberOfBathrooms;
    private TextView numberOfBedrooms;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        View root = inflater.inflate(R.layout.fragment_estate_details, container, false);
        this.surface = root.findViewById(R.id.surface_value);
        this.numberOfRooms = root.findViewById(R.id.rooms_value);
        this.numberOfBathrooms = root.findViewById(R.id.bathrooms_value);
        this.numberOfBedrooms = root.findViewById(R.id.bedrooms_value);

        this.sharedViewModel.getEstateSelection().observe(this.getViewLifecycleOwner(), estateSelection -> {
            this.surface.setText(estateSelection.getSurface().toString());
            this.numberOfRooms.setText(estateSelection.getNumberOfRooms().toString());
            this.numberOfBathrooms.setText(estateSelection.getNumberOfBathrooms().toString());
            this.numberOfBedrooms.setText(estateSelection.getNumberOfBedrooms().toString());
        });

        return root;
    }
}
