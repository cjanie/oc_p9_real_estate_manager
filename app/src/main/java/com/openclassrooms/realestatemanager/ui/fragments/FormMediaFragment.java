package com.openclassrooms.realestatemanager.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.PhotosRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FormMediaFragment extends FormSectionFragment implements View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_media;

    private ImageView photo;
    private List<Bitmap> bitmaps;
    private RecyclerView photosRecyclerView;
    private PhotosRecyclerViewAdapter photosAdapter;

    private Button camera;
    private Button save;
    private Button skip;

    private HandleMediaData handleMediaData;

    private ActivityResultLauncher launcherRequestPermissionForCamera = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                this.launcherStartCameraActivityForResult.launch(cameraIntent);
            });

    private ActivityResultLauncher launcherStartCameraActivityForResult = this.registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if(intent.getExtras() != null) {
                            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                            bitmaps.add(bitmap);
                            photo.setImageBitmap(bitmap);
                        }
                    }

                }
            }
    );

    private final String PERMISSION = Manifest.permission.CAMERA;

    public FormMediaFragment(HandleMediaData handleMediaData, SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
        this.handleMediaData = handleMediaData;
        this.bitmaps = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.photo = root.findViewById(R.id.image_form_media);
        this.photosRecyclerView = root.findViewById(R.id.recyclerView_form_media);
        this.camera = root.findViewById(R.id.button_form_camera);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);

        this.photosAdapter = new PhotosRecyclerViewAdapter(this.bitmaps);
        this.photosRecyclerView.setAdapter(photosAdapter);

        this.camera.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_form_camera) {
            this.launcherRequestPermissionForCamera.launch(this.PERMISSION);

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

    interface HandleMediaData {
        void setEstateMediaData(List<String> media);
    }
}
