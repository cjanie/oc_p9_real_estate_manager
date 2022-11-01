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

public class FormMediaFragment extends Fragment implements View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_media;

    private Button camera;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.camera = root.findViewById(R.id.button_form_camera);

        this.camera.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_form_camera) {
            // TODO camera permission request
        }
    }
}
