package com.openclassrooms.realestatemanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;

import java.util.List;

public abstract class FormSaveSkipFragment extends Fragment implements FormStep {

    private Next next;

    private FormData formData;

    protected SaveEstateDataUpdate saveEstateDataUpdate;

    public FormSaveSkipFragment(SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
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

    protected void enableButton(Button button) {
        button.setEnabled(true);
        button.setAlpha(1);
    }

    protected void hideButton(Button button) {
        button.setVisibility(View.GONE);
    }

    protected void showButton(Button button) {
        button.setVisibility(View.VISIBLE);
    }
}
