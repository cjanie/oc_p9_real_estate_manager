package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.text.Editable;
import android.text.TextWatcher;

import com.openclassrooms.realestatemanager.ui.fragments.Next;

public abstract class FormOnTextChangedFragment extends FormSaveSkipFragment implements
        TextWatcher {

    public FormOnTextChangedFragment(SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.enableButtonSave();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    protected abstract void enableButtonSave();
}
