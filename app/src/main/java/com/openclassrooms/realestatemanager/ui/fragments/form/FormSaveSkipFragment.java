package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.fragments.Next;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormData;
import com.openclassrooms.realestatemanager.ui.fragments.form.FormStep;
import com.openclassrooms.realestatemanager.ui.fragments.form.SaveEstateDataUpdate;

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
