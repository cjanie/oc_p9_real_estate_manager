package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Bundle;
import android.text.TextUtils;
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

public class FormDescriptionFragment extends FormOnTextChangedFragment implements
        View.OnClickListener {

    private EditText description;

    private ImageButton delete;

    private Button save;

    private Button skip;

    private HandleDescriptionData handleDescriptionData;

    private FormDelete formDelete;

    public FormDescriptionFragment(
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData,
            HandleDescriptionData handleDescriptionData,
            FormDelete formDelete
            ) {
        super(saveEstateDataUpdate, next, formData);
        this.handleDescriptionData = handleDescriptionData;
        this.formDelete = formDelete;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form_description, container, false);

        this.description = root.findViewById(R.id.editText_form_description);
        this.delete = root.findViewById(R.id.button_delete);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);

        this.description.addTextChangedListener(this);
        this.delete.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        Estate currentEstate = this.getFormData();
        if(currentEstate != null) {
            this.description.setText(currentEstate.getDescription());
        }

        return root;
    }

    @Override
    protected void enableButtonSave() {
        this.enableButton(this.save);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_delete) {
            this.formDelete.delete(FormField.DESCRIPTION);
            this.description.setText("");
        } else if(view.getId() == R.id.button_save_form) {
            if(!TextUtils.isEmpty(this.description.getText())) {
                this.save();
                this.next();
            }

        } else if(view.getId() == R.id.button_skip_form) {
            this.next();
        }
    }

    @Override
    protected void save() {
        String description = null;
        if(!TextUtils.isEmpty(this.description.getText())) {
            description = this.description.getText().toString();
        }
        this.handleDescriptionData.setEstateDescriptionData(description);
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
