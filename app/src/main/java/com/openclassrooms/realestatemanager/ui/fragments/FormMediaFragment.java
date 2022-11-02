package com.openclassrooms.realestatemanager.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.PhotosRecyclerViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FormMediaFragment extends FormSectionFragment implements View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_media;

    private RecyclerView photosRecyclerView;
    private PhotosRecyclerViewAdapter photosAdapter;
    private List<Bitmap> photos;

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
                            Bitmap photo = (Bitmap) intent.getExtras().get("data");
                            photos.add(photo);
                            enableSaveButton();
                            photosAdapter.updateList(photos);
                        }
                    }

                }
            }
    );

    private final String PERMISSION = Manifest.permission.CAMERA;

    public FormMediaFragment(HandleMediaData handleMediaData, SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
        this.handleMediaData = handleMediaData;
        this.photos = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(this.LAYOUT_ID, container, false);
        this.photosRecyclerView = root.findViewById(R.id.recyclerView_form_media);
        this.camera = root.findViewById(R.id.button_form_camera);
        this.save = root.findViewById(R.id.button_save_form);
        this.skip = root.findViewById(R.id.button_skip_form);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        this.photosRecyclerView.setLayoutManager(horizontalLayoutManager);

        this.photosAdapter = new PhotosRecyclerViewAdapter();

        this.photosRecyclerView.setAdapter(photosAdapter);

        this.camera.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.skip.setOnClickListener(this);

        /*
        * To see saved images in the gallery view
        * sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                         Uri.parse("file://" + Environment.getExternalStorageDirectory())));*/

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
        /*
        List<String> media = this.getFormData().getMedia();
        // TODO get the list of media
        if(!media.isEmpty()) {
            this.handleMediaData.setEstateMediaData(media);
            this.saveEstateDataUpdate.saveEstateDataUpdate();
        }

         */
        for(Bitmap photo: this.photos) {
            this.saveImage(photo);
        }
    }

    private void saveImage(Bitmap bitmap) {
        // https://stackoverflow.com/questions/7887078/android-saving-file-to-external-storage

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this.getContext(), new String[] { file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    private void enableSaveButton() {
        this.save.setEnabled(true);
        this.save.setAlpha(1);
    }

    interface HandleMediaData {
        void setEstateMediaData(List<String> media);
    }
}
