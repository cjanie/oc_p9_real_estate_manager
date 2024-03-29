package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;
import com.openclassrooms.realestatemanager.ui.adapters.MediaReadRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.viewmodels.DetailsViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.DetailsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class EstateDetailsFragment extends BaseFragment {

    private final int LAYOUT_ID = R.layout.fragment_details;

    protected SharedViewModel sharedViewModel;
    protected DetailsViewModel detailsViewModel;

    private RecyclerView photosRecyclerView;
    private MediaReadRecyclerViewAdapter mediaRecyclerViewAdapter;

    private TextView status;
    private TextView statusDate;
    private TextView agent;

    private TextView surface;
    private TextView numberOfRooms;
    private TextView numberOfBathrooms;
    private TextView numberOfBedrooms;

    private TextView streetNumberAndName;
    private TextView addressComplements;
    private TextView city;
    private TextView zipCode;
    private TextView country;

    private TextView description;

    private LinearLayout flexLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        DetailsViewModelFactory detailsViewModelFactory = ((Launch)this.getActivity().getApplication()).detailsViewModelFactory();
        this.detailsViewModel = new ViewModelProvider(this.getActivity(), detailsViewModelFactory).get(DetailsViewModel.class);

        View root = inflater.inflate(this.LAYOUT_ID, container, false);

        this.photosRecyclerView = root.findViewById(R.id.recyclerView_media);

        this.status = root.findViewById(R.id.estate_status);
        this.statusDate = root.findViewById(R.id.estate_status_date);
        this.agent = root.findViewById(R.id.estate_agent);

        this.description = root.findViewById(R.id.estate_decription_content);

        this.surface = root.findViewById(R.id.surface_value);
        this.numberOfRooms = root.findViewById(R.id.rooms_value);
        this.numberOfBathrooms = root.findViewById(R.id.bathrooms_value);
        this.numberOfBedrooms = root.findViewById(R.id.bedrooms_value);

        this.streetNumberAndName = root.findViewById(R.id.location_number_street);
        this.addressComplements = root.findViewById(R.id.address_complements);
        this.city = root.findViewById(R.id.location_city);
        this.zipCode = root.findViewById(R.id.location_zip_code);
        this.country = root.findViewById(R.id.country);

        this.flexLayout = root.findViewById(R.id.details_flex_layout);

        this.handleTabletMode();

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        this.photosRecyclerView.setLayoutManager(horizontalLayoutManager);

        this.mediaRecyclerViewAdapter = new MediaReadRecyclerViewAdapter();
        this.photosRecyclerView.setAdapter(this.mediaRecyclerViewAdapter);

        this.detailsViewModel.getEstate().observe(this.getActivity(), estate -> {

            List<Media> mediaList = new ArrayList<>();

            if(!estate.getMediaList().isEmpty()) {
                for(Media media: estate.getMediaList()) {
                    mediaList.add(media);
                }
            }

            this.mediaRecyclerViewAdapter.updateList(mediaList);

            this.status.setText(estate.getStatus().toString());

            if(estate.getStatus().equals(EstateStatus.SALE)) {
                String startDate = estate.getDateOfEntreeIntoMarket().toString();
                this.statusDate.setText(startDate);
            } else {
                String dateOfSale = estate.getDateOfSale().toString();
                this.statusDate.setText(dateOfSale);
            }

            this.agent.setText(estate.getAgent().getName());

            if(estate.getDescription() != null) {
                this.description.setText(estate.getDescription());
            }
            if(estate.getSurface() != null) {
                this.surface.setText(estate.getSurface().toString());
            }
            if(estate.getNumberOfRooms() != null) {
                this.numberOfRooms.setText(estate.getNumberOfRooms().toString());
            }
            if(estate.getNumberOfBathrooms() != null) {
                this.numberOfBathrooms.setText(estate.getNumberOfBathrooms().toString());
            }
            if(estate.getNumberOfBedrooms() != null) {
                this.numberOfBedrooms.setText(estate.getNumberOfBedrooms().toString());
            }
            if(estate.getStreetNumberAndStreetName() != null) {
                this.streetNumberAndName.setText(estate.getStreetNumberAndStreetName());
            }
            if(estate.getAddressComplements() != null) {
                this.addressComplements.setText(estate.getAddressComplements());
            }
            if(estate.getLocation() != null) {
                this.city.setText(estate.getLocation());
            }
            if(estate.getZipCode() != null) {
                this.zipCode.setText(estate.getZipCode());
            }
            if(estate.getCountry() != null) {
                this.country.setText(estate.getCountry());
            }

            MapsFragment mapsFragment = new MapEstateFragment(estate);
            if(this.isAdded()) {
                this.getChildFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_google_map, mapsFragment)
                        .commit();

            }


        });


        this.sharedViewModel.getEstateSelectionId().observe(this.getViewLifecycleOwner(), estateSelectionId -> {
            this.detailsViewModel.fetchEstateToUpdateLiveData(estateSelectionId);
        });

        return root;
    }

    private void handleTabletMode() {
        boolean isTablet = this.getResources().getBoolean(R.bool.is_tablet);
        if(isTablet) {
            this.flexLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
    }
}
