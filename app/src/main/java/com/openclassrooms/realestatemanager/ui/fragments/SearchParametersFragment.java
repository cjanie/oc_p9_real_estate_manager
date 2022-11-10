package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchParametersFragment extends Fragment implements View.OnClickListener {

    private AutoCompleteTextView selectType;

    private AutoCompleteTextView selectLocation;

    private Button search;

    // Selected values as parameters
    private EstateType typeValue;

    private String locationValue;

    // Interfaces

    private HandleSearchParameters handleSearchParameters;

    private HandleSearchRequest handleSearchRequest;

    // Constructor

    public SearchParametersFragment(
            HandleSearchParameters handleSearchParameters,
            HandleSearchRequest handleSearchRequest) {
        this.handleSearchParameters = handleSearchParameters;
        this.handleSearchRequest = handleSearchRequest;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search_parameters, container, false);
        this.selectType = root.findViewById(R.id.auto_complete_estate_type);
        this.selectLocation = root.findViewById(R.id.auto_complete_estate_location);
        this.search = root.findViewById(R.id.button_search);

        // set types in view
        List<String> types = new ArrayList<>();
        for (EstateType type: EstateType.values()) {
            types.add(type.toString());
        }

        ArrayAdapter<String> typesAdapter = new ArrayAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, types
        );
        this.selectType.setAdapter(typesAdapter);

        // set locations in view
        List<String> locations = Arrays.asList(
                "Paris", "Montpellier", "Orange"
        );
        ArrayAdapter<String> locationsAdapter = new ArrayAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, locations
        );
        this.selectLocation.setAdapter(locationsAdapter);

        // set listeners
        this.selectType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type = (String) adapterView.getAdapter().getItem(i);
                Map<String, EstateType> mapTypeByString = new HashMap<>();
                for(EstateType estateType: EstateType.values()) {
                    mapTypeByString.put(estateType.toString(), estateType);
                }
                if(mapTypeByString.keySet().contains(type)) {
                    typeValue = mapTypeByString.get(type);
                }
            }
        });

        this.selectLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                locationValue = (String) adapterView.getAdapter().getItem(i);
            }
        });

        this.search.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if(this.typeValue != null) {
            this.handleSearchParameters.setParamType(this.typeValue);
        }
        if(this.locationValue != null) {
            this.handleSearchParameters.setParamLocation(this.locationValue);
        }
        this.handleSearchRequest.search();
    }

    interface HandleSearchParameters {
        void setParamType(EstateType type);
        void setParamLocation(String location);
        void setParamMaxPrice(Float maxPrice);
    }

    interface HandleSearchRequest {
        void search();
    }
}
