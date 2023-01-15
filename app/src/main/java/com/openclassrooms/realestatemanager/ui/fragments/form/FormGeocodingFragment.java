package com.openclassrooms.realestatemanager.ui.fragments.form;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.realestatemanager.Launch;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.MapEstateFragment;
import com.openclassrooms.realestatemanager.ui.fragments.MapEstateWifiFragment;
import com.openclassrooms.realestatemanager.ui.fragments.MapsFragment;
import com.openclassrooms.realestatemanager.ui.fragments.Next;
import com.openclassrooms.realestatemanager.ui.utils.WorkStore;
import com.openclassrooms.realestatemanager.ui.utils.WorkStoreGatewayConfig;

import java.util.HashSet;
import java.util.Set;

public class FormGeocodingFragment extends FormSaveSkipFragment implements View.OnClickListener {

    private Button skip;
    private Button save;
    private MapsFragment mapsFragment;

    public FormGeocodingFragment(SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_geocoding, container, false);
        this.skip = root.findViewById(R.id.button_skip_form);
        this.save = root.findViewById(R.id.button_save_form);

        this.skip.setOnClickListener(this);
        this.save.setOnClickListener(this);

        if(this.getFormData().getLatitude() != null && this.getFormData().getLongitude() != null) {
            this.mapsFragment = new MapEstateFragment(this.getFormData());
        } else {
            this.mapsFragment = new MapEstateWifiFragment(this.getFormData());
            this.enableButton(this.save);
        }

        this.showMap();
        return root;
    }

    private void showMap() {
        FragmentManager fragmentManager = this.getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout_form_map, this.mapsFragment)
                .commit();
    }

    @Override
    protected void save() {
        this.saveEstateDataUpdate.saveEstateDataUpdate();
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.GEOCODING;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_save_form) {
            this.save();
            this.next();
        } else {
            this.next();
        }
    }
}
