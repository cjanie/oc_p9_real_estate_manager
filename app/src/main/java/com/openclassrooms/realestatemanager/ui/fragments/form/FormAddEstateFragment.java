package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateStatus;

import java.time.LocalDate;

public class FormAddEstateFragment extends FormFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        this.formViewModel.setEstateData(new Estate());
        return root;
    }

    @Override
    public Estate getInitializedEstate() {
        Estate estate = new Estate();
        return estate;
    }



}
