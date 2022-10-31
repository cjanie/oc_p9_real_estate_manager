package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

public class FormDescriptionDetailsFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_description_details;

    private HandleDescriptionDetailsData handleDescriptionDetailsData;

    private SaveEstateDataUpdate saveEstateDataUpdate;

    private Next next;

    private FormData formData;

    // Views
    private EditText surface;

    private EditText numberOfRooms;

    private EditText numberOfBathrooms;

    private EditText numberOfBedrooms;

    private Button save;

    private Button skip;

    // Constructor
    public FormDescriptionDetailsFragment(
            HandleDescriptionDetailsData handleDescriptionDetailsData,
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData
    ) {
        this.handleDescriptionDetailsData = handleDescriptionDetailsData;
        this.saveEstateDataUpdate = saveEstateDataUpdate;
        this.next = next;
        this.formData = formData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);

        this.surface = root.findViewById(R.id.editText_surface);
        this.numberOfRooms = root.findViewById(R.id.editText_numberOfRooms);
        this.numberOfBathrooms = root.findViewById(R.id.editText_numberOfBathrooms);
        this.numberOfBedrooms = root.findViewById(R.id.editText_numberOfBedrooms);
        this.save = root.findViewById(R.id.button_save_form_description_details);
        this.skip = root.findViewById(R.id.button_skip_form_description_details);

        this.surface.addTextChangedListener(this);
        this.numberOfRooms.addTextChangedListener(this);
        this.numberOfBathrooms.addTextChangedListener(this);
        this.numberOfBedrooms.addTextChangedListener(this);

        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        Estate currentEstate = this.formData.getData();
        if(currentEstate != null) {
            if(currentEstate.getSurface() != null) {
                this.surface.setText(currentEstate.getSurface().toString());
            }
            if(currentEstate.getNumberOfRooms() != null) {
                this.numberOfRooms.setText(currentEstate.getNumberOfRooms().toString());
            }
            if(currentEstate.getNumberOfBathrooms() != null) {
                this.numberOfBathrooms.setText(currentEstate.getNumberOfBathrooms().toString());
            }
            if(currentEstate.getNumberOfBedrooms() != null) {
                this.numberOfBedrooms.setText(currentEstate.getNumberOfBedrooms().toString());
            }
        }

        return root;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length() > 0) {
            save.setEnabled(true);
            save.setAlpha(1);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_save_form_description_details) {
            try {
                Integer surface = this.checkNumericField(this.surface);
                Integer numberOfRooms = this.checkNumericField(this.numberOfRooms);
                Integer numberOfBathrooms = this.checkNumericField(this.numberOfBathrooms);
                Integer numberOfBedrooms = this.checkNumericField(this.numberOfBedrooms);

                this.handleDescriptionDetailsData.setDescriptionDetailsData(
                        surface,
                        numberOfRooms,
                        numberOfBathrooms,
                        numberOfBedrooms
                );
                this.saveEstateDataUpdate.saveEstateDataUpdate();
                this.next.next(this);
            } catch (NumberFormatException e) {

            }

        } else if(view.getId() == R.id.button_skip_form_description_details) {
            this.next.next(this);
        }
    }

    private Integer checkNumericField(EditText editText) throws NumberFormatException {
        if(!TextUtils.isEmpty(editText.getText())) {
            try {
                return Integer.parseInt(editText.getText().toString());
            } catch (NumberFormatException e) {
                editText.setError(this.getString(R.string.nan));
                throw e;
            }
        }
        return null;

    }


    interface HandleDescriptionDetailsData {
        void setDescriptionDetailsData(
                Integer surface,
                Integer numberOfRooms,
                Integer numberOfBathrooms,
                Integer numberOfBedrooms);
    }
}
