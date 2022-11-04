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

public class FormDescriptionFragment extends FormSaveSkipFragment implements View.OnClickListener, TextWatcher {

    private EditText description;

    private Button save;

    private Button skip;

    private HandleDescriptionData handleDescriptionData;

    public FormDescriptionFragment(
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData,
            HandleDescriptionData handleDescriptionData
            ) {
        super(saveEstateDataUpdate, next, formData);
        this.handleDescriptionData = handleDescriptionData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form_description, container, false);

        this.description = root.findViewById(R.id.editText_form_description);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);

        this.description.addTextChangedListener(this);
        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        Estate currentEstate = this.getFormData();
        if(currentEstate != null) {
            if(currentEstate.getDescription() != null) {
                this.description.setText(currentEstate.getDescription());
            }
        }

        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_save_form) {
            if(!TextUtils.isEmpty(this.description.getText())) {
                this.save();
                this.next();
            }

        } else if(view.getId() == R.id.button_skip_form) {
            this.next();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.enableButton(this.save);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    protected void save() {
        this.handleDescriptionData.setEstateDescriptionData(this.description.getText().toString());
        this.saveEstateDataUpdate.saveEstateDataUpdate();
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.DESCRIPTION;
    }

    interface HandleDescriptionData {
        void setEstateDescriptionData(String description);
    }
}
