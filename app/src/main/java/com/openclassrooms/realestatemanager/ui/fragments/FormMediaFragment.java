package com.openclassrooms.realestatemanager.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class FormMediaFragment extends FormSectionFragment implements View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_media;

    private Button camera;
    private Button save;
    private Button skip;

    private HandleMediaData handleMediaData;

    private ActivityResultLauncher activityResultLauncher = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                startCameraActivityWithPermission();
            });

    private final String PERMISSION = Manifest.permission.CAMERA;

    public FormMediaFragment(HandleMediaData handleMediaData, SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
        this.handleMediaData = handleMediaData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.camera = root.findViewById(R.id.button_form_camera);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);

        this.camera.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_form_camera) {
            this.activityResultLauncher.launch(this.PERMISSION);

        } else if(view.getId() == R.id.button_save_form) {
            this.save();

        } else if(view.getId() == R.id.button_skip_form) {
            this.next();
        }
    }

    @Override
    public void save() {
        List<String> media = this.getFormData().getMedia();
        // TODO get the list of media
        if(!media.isEmpty()) {
            this.handleMediaData.setEstateMediaData(media);
            this.saveEstateDataUpdate.saveEstateDataUpdate();
        }
    }

    private void startCameraActivityWithPermission() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(cameraIntent);
    }



    interface HandleMediaData {
        void setEstateMediaData(List<String> media);
    }
}
