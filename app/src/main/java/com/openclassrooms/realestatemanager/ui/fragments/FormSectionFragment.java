package com.openclassrooms.realestatemanager.ui.fragments;

import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

import java.util.List;

public abstract class FormSectionFragment extends Fragment {

    protected SaveEstateDataUpdate saveEstateDataUpdate;
    private Next next;

    private FormData formData;

    public FormSectionFragment() {
    }

    public FormSectionFragment(SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        this.saveEstateDataUpdate = saveEstateDataUpdate;
        this.next = next;
        this.formData = formData;
    }

    protected void next() {
        this.next.next(this);
    }

    protected Estate getFormData() {
        return this.formData.getData();
    }

    protected abstract void save();
}
