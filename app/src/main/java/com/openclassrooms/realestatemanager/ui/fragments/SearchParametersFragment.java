package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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

    private EditText editMaxPrice;

    private Button search;

    private Button reset;

    // Interfaces

    private HandleSearchParameters handleSearchParameters;

    private HandleResetParameters handleResetParameters;

    private HandleSearchRequest handleSearchRequest;

    // Constructor

    public SearchParametersFragment(
            HandleSearchParameters handleSearchParameters,
            HandleResetParameters handleResetParameters,
            HandleSearchRequest handleSearchRequest) {
        this.handleSearchParameters = handleSearchParameters;
        this.handleSearchRequest = handleSearchRequest;
        this.handleResetParameters = handleResetParameters;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search_parameters, container, false);
        this.selectType = root.findViewById(R.id.auto_complete_estate_type);
        this.selectLocation = root.findViewById(R.id.auto_complete_estate_location);
        this.editMaxPrice = root.findViewById(R.id.edit_text_estate_max_price);
        this.search = root.findViewById(R.id.button_search);
        this.reset = root.findViewById(R.id.button_reset);

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
                    EstateType typeValue = mapTypeByString.get(type);
                    handleSearchParameters.setParamType(typeValue);
                }
            }
        });

        this.selectLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String locationValue = (String) adapterView.getAdapter().getItem(i);
                handleSearchParameters.setParamLocation(locationValue);
            }
        });

        this.editMaxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    try {
                        Float maxPriceValue = Float.parseFloat(charSequence.toString());
                        handleSearchParameters.setParamMaxPrice(maxPriceValue);
                    } catch (NumberFormatException e) {
                        editMaxPrice.setError(getString(R.string.nan));
                    }
                } else handleSearchParameters.setParamMaxPrice(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        this.search.setOnClickListener(this);
        this.reset.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_search) {
            this.handleSearchRequest.search();
            this.reset();
        } else {
            this.reset();
        }
    }

    private void reset() {
        this.handleResetParameters.reset();

        this.selectType.setText("");
        this.selectLocation.setText("");
        this.editMaxPrice.setText("");
    }



    interface HandleSearchParameters {
        void setParamType(EstateType type);
        void setParamLocation(String location);
        void setParamMaxPrice(Float maxPrice);
    }

    interface HandleResetParameters {
        void reset();
    }

    interface HandleSearchRequest {
        void search();
    }
}
