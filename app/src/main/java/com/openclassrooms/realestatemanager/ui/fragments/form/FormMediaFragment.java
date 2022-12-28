package com.openclassrooms.realestatemanager.ui.fragments.form;

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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;
import com.openclassrooms.realestatemanager.ui.adapters.MediaWriteRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.adapters.PhotosRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.ui.fragments.Next;
import com.openclassrooms.realestatemanager.ui.utils.StorageManagerUtil;

import java.util.ArrayList;
import java.util.List;

public class FormMediaFragment extends FormSaveSkipFragment implements
        View.OnClickListener,
        MediaWriteRecyclerViewAdapter.UpdateMedia {

    private final int LAYOUT_ID = R.layout.fragment_form_media;

    private RecyclerView photosRecyclerView;
    private PhotosRecyclerViewAdapter photosAdapter;
    private List<Bitmap> photos;

    private MediaWriteRecyclerViewAdapter mediaRecyclerViewAdapter;
    private List<Media> mediaList;

    private Button camera;

    private Button save;
    private Button skip;

    private HandleMediaData handleMediaData;

    private StorageManagerUtil storageManagerUtil;

    private final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    private final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;


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
                            Bitmap photo = (Bitmap) intent.getExtras().get("data");
                            photosRecyclerView.setAdapter(photosAdapter);
                            photos.add(photo);
                            enableButton(save);
                            photosAdapter.updateList(photos);
                        }
                    }

                }
            }
    );

    private ActivityResultLauncher launcherRequestPermissionToWriteStorage = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                this.save();
            }
    );

    // Constructor
    public FormMediaFragment(HandleMediaData handleMediaData, SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
        this.handleMediaData = handleMediaData;
        this.storageManagerUtil = new StorageManagerUtil();

        this.photos = new ArrayList<>();
        this.mediaList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.photosRecyclerView = root.findViewById(R.id.recyclerView_media);
        this.camera = root.findViewById(R.id.button_form_camera);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        this.photosRecyclerView.setLayoutManager(horizontalLayoutManager);

        this.photosAdapter = new PhotosRecyclerViewAdapter();
        this.mediaRecyclerViewAdapter = new MediaWriteRecyclerViewAdapter(this);
        //this.photosRecyclerView.setAdapter(photosAdapter);
        this.photosRecyclerView.setAdapter(mediaRecyclerViewAdapter);

        this.camera.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        // Read media when update mode
        this.mediaList = this.getFormData().getMediaList();
        this.mediaRecyclerViewAdapter.updateList(this.mediaList);
        return root;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_form_camera) {
            this.launcherRequestPermissionForCamera.launch(this.PERMISSION_CAMERA);

        } else if(view.getId() == R.id.button_save_form) {
            this.launcherRequestPermissionToWriteStorage.launch(this.PERMISSION_WRITE_STORAGE);
            if(this.isMediaFormComplete()) {
                this.next();
            }

        } else if(view.getId() == R.id.button_skip_form) {
            this.next();
        }
    }

    @Override
    public void save() {

        if(!this.photos.isEmpty()) {
            // save photos in storage and get the path
            List<String> paths = new ArrayList<>();
            for(Bitmap photo: this.photos) {
                String path = this.saveImageInStorage(photo);
                paths.add(path);
            }
            // Save file paths in db
            List<Media> newMediaList = new ArrayList<>();
            if(!paths.isEmpty()) {
                for(String path: paths) {
                    Media media = new Media();
                    media.setPath(path);
                    newMediaList.add(media);
                }
            }
            this.saveFilePathsInDb(newMediaList);
            this.photos.clear();

            // Change adapter to update media with name
            this.mediaList = this.getFormData().getMediaList();
            this.photosRecyclerView.setAdapter(mediaRecyclerViewAdapter);
            mediaRecyclerViewAdapter.updateList(this.mediaList);

        } else if(!this.mediaList.isEmpty()) {
            this.handleMediaData.setEstateMediaData(this.mediaList);
            this.saveEstateDataUpdate.saveEstateDataUpdate();
        }
    }

    private String saveImageInStorage(Bitmap bitmap) {
        String path = this.storageManagerUtil.saveImageInStorage(bitmap, this.getContext());
        return path;
    }

    private void saveFilePathsInDb(List<Media> mediaList) {
        List<Media> mediaListExisting = this.getFormData().getMediaList();

        List<Media> mediaListUpdate = new ArrayList<>();
        mediaListUpdate.addAll(mediaListExisting);
        mediaListUpdate.addAll(mediaList);

        this.handleMediaData.setEstateMediaData(mediaListUpdate);
        this.saveEstateDataUpdate.saveEstateDataUpdate();
    }

    private boolean isMediaFormComplete() {
        if(!this.mediaList.isEmpty()) {
            for(Media media: this.mediaList) {
                if(media.getName() == null || media.getName().isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void updateMedia(Media media) {
        this.mediaList = this.getFormData().getMediaList();
        if(!this.mediaList.isEmpty()) {
            for(Media m: mediaList) {
                if(m.getPath().equals(media.getPath())) {
                    m.setName(media.getName());
                    break;
                }
            }
        }
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.MEDIA;
    }

    @Override
    public void enableSaveButton() {
        this.enableButton(this.save);
    }

    interface HandleMediaData {
        void setEstateMediaData(List<Media> mediaList);
    }
}
