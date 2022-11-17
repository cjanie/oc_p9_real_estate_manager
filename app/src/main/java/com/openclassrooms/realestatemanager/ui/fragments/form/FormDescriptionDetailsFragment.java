package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.fragments.Next;

public class  FormDescriptionDetailsFragment extends FormOnTextChangedFragment implements
        View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_description_details;

    private HandleDescriptionDetailsData handleDescriptionDetailsData;

    private FormDelete formDelete;

    // Views
    private EditText surface;

    private EditText numberOfRooms;

    private EditText numberOfBathrooms;

    private EditText numberOfBedrooms;

    private ImageButton deleteSurface;

    private ImageButton deleteNumberOfRooms;

    private ImageButton deleteNumberOfBathrooms;

    private ImageButton deleteNumberOfBedrooms;

    private Button save;

    private Button skip;

    // Constructor
    public FormDescriptionDetailsFragment(
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData,
            HandleDescriptionDetailsData handleDescriptionDetailsData,
            FormDelete formDelete
    ) {
        super(saveEstateDataUpdate, next, formData);
        this.handleDescriptionDetailsData = handleDescriptionDetailsData;
        this.formDelete = formDelete;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);

        this.surface = root.findViewById(R.id.editText_surface);
        this.numberOfRooms = root.findViewById(R.id.editText_numberOfRooms);
        this.numberOfBathrooms = root.findViewById(R.id.editText_numberOfBathrooms);
        this.numberOfBedrooms = root.findViewById(R.id.editText_numberOfBedrooms);

        this.deleteSurface = root.findViewById(R.id.button_delete_surface);
        this.deleteNumberOfRooms = root.findViewById(R.id.button_delete_rooms);
        this.deleteNumberOfBathrooms = root.findViewById(R.id.button_delete_bathrooms);
        this.deleteNumberOfBedrooms = root.findViewById(R.id.button_delete_bedrooms);

        this.save = root.findViewById(R.id.button_save_form_description_details);
        this.skip = root.findViewById(R.id.button_skip_form_description_details);

        this.surface.addTextChangedListener(this);
        this.numberOfRooms.addTextChangedListener(this);
        this.numberOfBathrooms.addTextChangedListener(this);
        this.numberOfBedrooms.addTextChangedListener(this);

        this.deleteSurface.setOnClickListener(this);
        this.deleteNumberOfRooms.setOnClickListener(this);
        this.deleteNumberOfBathrooms.setOnClickListener(this);
        this.deleteNumberOfBedrooms.setOnClickListener(this);

        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        Estate currentEstate = this.getFormData();
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
    public void onClick(View view) {
        // Delete buttons
        if(view.getId() == R.id.button_delete_surface) {
            this.formDelete.delete(FormField.SURFACE);
            this.surface.setText("");
        } else if(view.getId() == R.id.button_delete_rooms) {
            this.formDelete.delete(FormField.ROOMS);
            this.numberOfRooms.setText("");
        } else if(view.getId() == R.id.button_delete_bathrooms) {
            this.formDelete.delete(FormField.BATHROOMS);
            this.numberOfBathrooms.setText("");
        } else if(view.getId() == R.id.button_delete_bedrooms) {
            this.formDelete.delete(FormField.BEDROOMS);
            this.numberOfBedrooms.setText("");

        } else if(view.getId() == R.id.button_save_form_description_details) {
            try {
                this.save();
                this.next();
            } catch (NumberFormatException e) {

            }

        } else if(view.getId() == R.id.button_skip_form_description_details) {
            this.next();
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

    @Override
    protected void enableButtonSave() {
        this.enableButton(this.save);
    }

    @Override
    protected void save() {
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
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.DESCRIPTION_DETAILS;
    }


    interface HandleDescriptionDetailsData {
        void setDescriptionDetailsData(
                Integer surface,
                Integer numberOfRooms,
                Integer numberOfBathrooms,
                Integer numberOfBedrooms);
    }
}
