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

    private AutoCompleteTextView type;

    private AutoCompleteTextView location;

    private Button search;


    private EstateType estateType;

    private String estateLocation;

    private HandleSearchParameters handleSearchParameters;

    private HandleSearchRequest handleSearchRequest;

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
        this.type = root.findViewById(R.id.auto_complete_estate_type);
        this.location = root.findViewById(R.id.auto_complete_estate_location);
        this.search = root.findViewById(R.id.button_search);

        // set types in view
        List<String> types = new ArrayList<>();
        for (EstateType type: EstateType.values()) {
            types.add(type.toString());
        }

        ArrayAdapter<String> typesAdapter = new ArrayAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, types
        );
        this.type.setAdapter(typesAdapter);

        // set locations in view
        List<String> locations = Arrays.asList(
                "PARIS", "MANCHESTER", "MONTPELLIER", "SETE", "ORANGE"
        );
        ArrayAdapter<String> locationsAdapter = new ArrayAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, locations
        );
        this.location.setAdapter(locationsAdapter);

        // set listeners
        this.type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type = (String) adapterView.getAdapter().getItem(i);
                Map<String, EstateType> mapTypeByString = new HashMap<>();
                for(EstateType estateType: EstateType.values()) {
                    mapTypeByString.put(estateType.toString(), estateType);
                }
                if(mapTypeByString.keySet().contains(type)) {
                    estateType = mapTypeByString.get(type);
                }
            }
        });

        this.location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                estateLocation = (String) adapterView.getAdapter().getItem(i);
            }
        });

        this.search.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if(this.estateType != null) {
            this.handleSearchParameters.setParamType(this.estateType);
        }
        if(this.estateLocation != null) {
            this.handleSearchParameters.setParamLocation(this.estateLocation);
        }
        this.handleSearchRequest.search();
    }

    interface HandleSearchParameters {
        void setParamType(EstateType type);
        void setParamLocation(String location);
    }

    interface HandleSearchRequest {
        void search();
    }
}
