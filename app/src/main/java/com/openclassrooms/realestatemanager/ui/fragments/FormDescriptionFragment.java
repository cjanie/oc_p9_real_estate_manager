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

public class FormDescriptionFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private EditText description;

    private Button save;

    private Button skip;

    private HandleDescriptionData handleDescriptionData;

    private SaveEstateDataUpdate saveEstateDataUpdate;

    private Next next;

    private FormData formData;

    public FormDescriptionFragment(
            HandleDescriptionData handleDescriptionData,
            SaveEstateDataUpdate saveEstateDataUpdate,
            Next next,
            FormData formData
            ) {
        this.handleDescriptionData = handleDescriptionData;
        this.saveEstateDataUpdate = saveEstateDataUpdate;
        this.next = next;
        this.formData = formData;
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

        Estate currentEstate = this.formData.getData();
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
                this.handleDescriptionData.setEstateDescriptionData(this.description.getText().toString());
                this.saveEstateDataUpdate.saveEstateDataUpdate();
                this.next.next(this);
            }

        } else if(view.getId() == R.id.button_skip_form) {
            this.next.next(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.save.setEnabled(true);
        this.save.setAlpha(1);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    interface HandleDescriptionData {
        void setEstateDescriptionData(String description);
    }
}
