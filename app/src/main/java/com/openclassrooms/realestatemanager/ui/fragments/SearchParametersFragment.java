package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchParametersFragment extends Fragment {

    private AutoCompleteTextView type;

    private AutoCompleteTextView location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_parameters, container, false);
        this.type = root.findViewById(R.id.auto_complete_estate_type);
        this.location = root.findViewById(R.id.auto_complete_estate_location);

        List<String> types = new ArrayList<>();
        for (EstateType type: EstateType.values()) {
            types.add(type.toString());
        }
        ArrayAdapter<String> typesAdapter = new ArrayAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, types
        );
        type.setAdapter(typesAdapter);

        List<String> locations = Arrays.asList(
                "PARIS", "MANCHESTER", "MONTPELLIER", "SETE", "ORANGE"
        );
        ArrayAdapter<String> locationsAdapter = new ArrayAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, locations
        );
        location.setAdapter(locationsAdapter);
        return root;
    }
}
