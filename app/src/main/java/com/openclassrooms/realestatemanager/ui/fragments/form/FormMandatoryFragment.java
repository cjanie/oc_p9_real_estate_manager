package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.ui.exceptions.MandatoryException;
import com.openclassrooms.realestatemanager.ui.fragments.Next;

import java.util.Arrays;
import java.util.List;

public class FormMandatoryFragment extends FormSaveSkipFragment implements AdapterView.OnItemClickListener  {

    private final int LAYOUT_ID = R.layout.fragment_form_mandatory_fields;

    private HandleFormMandatoryFields handleFormMandatoryFields;

    protected ConstraintLayout formMainLayout;

    protected AutoCompleteTextView type;

    protected EditText location;

    protected EditText price;

    private Button save;
    private Button skip;

    private String estateType;


    public FormMandatoryFragment(
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData,
            HandleFormMandatoryFields handleFormMandatoryFields
    ) {
        super(saveEstateDataUpdate, next, formData);
        this.handleFormMandatoryFields = handleFormMandatoryFields;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);

        this.formMainLayout = root.findViewById(R.id.frame_layout_form);
        this.type = root.findViewById(R.id.spinner_type);
        this.location = root.findViewById(R.id.editText_location);
        this.price = root.findViewById(R.id.editText_price);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);
        List<String> types = Arrays.asList(EstateType.FLAT.toString(), EstateType.DUPLEX.toString(), EstateType.HOUSE.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, types
        );
        this.type.setAdapter(adapter);
        this.type.setOnItemClickListener(this);

        this.hideButton(this.skip);
        Estate currentEstate = this.handleFormMandatoryFields.getInitializedEstate();
        if(currentEstate != null && currentEstate.getId() != null && currentEstate.getId() > 0) {
            this.estateType = currentEstate.getType().toString();
            this.type.setText(this.estateType);
            this.location.setText(currentEstate.getLocation());
            this.price.setText(currentEstate.getPrice().toString());
            this.showButton(this.skip);
        }

        this.enableButton(this.save);

        this.save.setOnClickListener(view -> {
            this.save();
            this.next();
        });
        this.skip.setOnClickListener(view -> {
            this.next();
        });

        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.estateType = (String) this.type.getAdapter().getItem(i);
    }

    protected EstateType getEstateType() throws MandatoryException {
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
        throw new MandatoryException();
    }

    @Override
    protected void save() {
        Estate estate = this.handleFormMandatoryFields.getInitializedEstate();
        String mandatoryFieldError = this.getString(R.string.required);

        // Type
        try {
            estate.setType(this.getEstateType());
        } catch (MandatoryException e) {
            this.type.setError(mandatoryFieldError);
        }
        // Location
        if(!TextUtils.isEmpty(this.location.getText())) {
            estate.setLocation(this.location.getText().toString());
        } else {
            this.location.setError(mandatoryFieldError);
        }
        // Price
        if(!TextUtils.isEmpty(this.price.getText())) {
            try {
                estate.setPrice(Float.parseFloat(this.price.getText().toString()));
            } catch (NumberFormatException e) {
                String nanError = this.getString(R.string.nan);
                this.price.setError(nanError);
            }
        } else {
            this.price.setError(mandatoryFieldError);
        }
        // Devise
        estate.setDevise(Devise.DOLLAR); // TODO Autocomplete field

        this.handleFormMandatoryFields.setMandatoryFields(estate);
        this.saveEstateDataUpdate.saveEstateDataUpdate();
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.MANDATORY;
    }


    interface HandleFormMandatoryFields {
        Estate getInitializedEstate();
        void setMandatoryFields(Estate estate);
    }


}
