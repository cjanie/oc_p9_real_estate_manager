package com.openclassrooms.realestatemanager.ui;

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

public class FormFragment extends Fragment implements AdapterView.OnItemClickListener{

    private FormViewModel formViewModel;

    private SharedViewModel sharedViewModel;

    private Integer id = 0;

    private AutoCompleteTextView type;

    private EditText location;

    private EditText price;

    private Button save;

    private EstateType estateType;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form, container, false);


        FormViewModelFactory viewModelFactory = ((Launch)this.getActivity().getApplication()).formViewModelFactory();
        this.formViewModel = new ViewModelProvider(this, viewModelFactory).get(FormViewModel.class);
        this.sharedViewModel = new ViewModelProvider(this.requireActivity()).get(SharedViewModel.class);

        this.type = root.findViewById(R.id.spinner_type);
        this.location = root.findViewById(R.id.editText_location);
        this.price = root.findViewById(R.id.editText_price);

        this.save = root.findViewById(R.id.button_save);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        List<String> types = new ArrayList<>();
        for(EstateType type: EstateType.values()) {
            types.add(type.toString());
        }
        /*
        types = Arrays.asList(EstateType.FLAT.toString(), EstateType.DUPLEX.toString(), EstateType.HOUSE.toString());

         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, types
        );
        this.type.setAdapter(adapter);
        this.type.setOnItemClickListener(this);

        this.save.setOnClickListener(view -> save());

        this.sharedViewModel.getMenuAction().observe(this.getViewLifecycleOwner(), action -> {
            if(action.equals(MenuAction.EDIT)) {
                this.sharedViewModel.getEstateSelection().observe(this.getViewLifecycleOwner(), estateSelection -> {
                    this.id = estateSelection.getId();
                    this.type.setText(estateSelection.getType().toString());
                    this.location.setText(estateSelection.getLocation());
                    this.price.setText(estateSelection.getPrice().toString());
                });
            } else {
                this.id = 0;
                this.type.setText("");
                this.location.setText("");
                this.price.setText("");
            }

        });

        return root;
    }

    private Long save() {
        Estate estate = new Estate();
        estate.setId(this.id);
        estate.setType(EstateType.FLAT); // TODO
        if(!TextUtils.isEmpty(this.location.getText())
                && !TextUtils.isEmpty(this.price.getText())) {
            estate.setLocation(this.location.getText().toString());
            try {
                estate.setPrice(Float.parseFloat(this.price.getText().toString()));
                estate.setDevise(Devise.DOLLAR);
                return this.formViewModel.saveEstate(estate);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return 0L;

        } else {
            return 0L;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.estateType = (EstateType) this.type.getAdapter().getItem(i);
    }
}
