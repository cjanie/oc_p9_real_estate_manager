package com.openclassrooms.realestatemanager.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.ui.viewmodels.FormViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.SharedViewModel;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.FormViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FormFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    protected FormViewModel formViewModel;

    protected Integer id = 0;

    protected AutoCompleteTextView type;

    protected EditText location;

    protected EditText price;

    protected EditText streetNumberAndStreetName;

    protected EditText addressComplements;

    protected EditText zipCode;

    protected EditText country;

    protected EditText surface;

    protected EditText numberOfRooms;

    protected EditText numberOfBathrooms;

    protected EditText numberOfBedrooms;

    private Button save;

    private String estateType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form, container, false);

        FormViewModelFactory formViewModelFactory = ((Launch)this.getActivity().getApplication()).formViewModelFactory();
        this.formViewModel = new ViewModelProvider(this, formViewModelFactory).get(FormViewModel.class);

        this.type = root.findViewById(R.id.spinner_type);
        this.location = root.findViewById(R.id.editText_location);
        this.price = root.findViewById(R.id.editText_price);
        this.streetNumberAndStreetName = root.findViewById(R.id.editText_streetNumber_and_streetName);
        this.addressComplements = root.findViewById(R.id.editText_addressComplements);
        this.zipCode = root.findViewById(R.id.editText_zipCode);
        this.country = root.findViewById(R.id.editText_country);
        this.surface = root.findViewById(R.id.editText_surface);
        this.numberOfRooms = root.findViewById(R.id.editText_numberOfRooms);
        this.numberOfBathrooms = root.findViewById(R.id.editText_numberOfBathrooms);
        this.numberOfBedrooms = root.findViewById(R.id.editText_numberOfBedrooms);

        this.save = root.findViewById(R.id.button_save);

        List<String> types = Arrays.asList(EstateType.FLAT.toString(), EstateType.DUPLEX.toString(), EstateType.HOUSE.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, types
        );
        this.type.setAdapter(adapter);
        this.type.setOnItemClickListener(this);

        this.save.setOnClickListener(view -> save());

        return root;
    }

    protected abstract Long save();

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.estateType = (String) this.type.getAdapter().getItem(i);
    }

    protected EstateType getEstateType() {
        if(this.estateType != null) {
            if(this.estateType.equals(EstateType.FLAT.toString())) {
                return EstateType.FLAT;
            }
            if(this.estateType.equals(EstateType.DUPLEX.toString())) {
                return EstateType.DUPLEX;
            }
            if(this.estateType.equals(EstateType.HOUSE.toString())) {
                return EstateType.HOUSE;
            }
        }

        return null;
    }
}
