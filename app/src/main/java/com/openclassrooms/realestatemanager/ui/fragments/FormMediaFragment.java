package com.openclassrooms.realestatemanager.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.PhotosRecyclerViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FormMediaFragment extends FormSaveSkipFragment implements View.OnClickListener {

    private final int LAYOUT_ID = R.layout.fragment_form_media;

    private final String STORAGE_ROOT_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
    private final String STORAGE_DIRECTORY_NAME = "rem_saved_images" ;
    private final String FILE_NAME_PREFIX = "rem_";
    private final String FILE_EXTENTION = ".jpg";

    private RecyclerView photosRecyclerView;
    private PhotosRecyclerViewAdapter photosAdapter;
    private List<Bitmap> photos;

    private Button camera;

    private Button save;
    private Button skip;

    private HandleMediaData handleMediaData;

    private final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    private final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private final String PERMISSION_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;


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

    // Constructeur
    public FormMediaFragment(HandleMediaData handleMediaData, SaveEstateDataUpdate saveEstateDataUpdate, Next next, FormData formData) {
        super(saveEstateDataUpdate, next, formData);
        this.handleMediaData = handleMediaData;

        this.photos = new ArrayList<>();

    }

    private ActivityResultLauncher launcherRequestPermissionToReadInStorage = this.registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermissionGranted -> {
                // TODO read Photo
                List<String> media = this.getFormData().getMedia();
                // TODO get the list of media when update mode
                List<Bitmap> bitmaps = new ArrayList<>();
                if(!media.isEmpty()) {
                    for(String m: media) {
                        File file = new File(m + this.FILE_EXTENTION);
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        bitmaps.add(bitmap);
                    }
                    this.photosAdapter.updateList(bitmaps);
                }


                // To see saved images in the gallery view
                // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));


            }
    );

    private ActivityResultLauncher launcherReadInStorage = this.registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent.getExtras() != null) {
                            Bitmap photo = (Bitmap) intent.getExtras().get("data");
                            photos.add(photo);
                            photosAdapter.updateList(photos);
                        }
                    }
                }
            }
    );

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

        //this.launcherRequestPermissionToReadInStorage.launch(this.PERMISSION_READ_STORAGE);

        // TODO read Photo
        List<String> media = this.getFormData().getMedia();
        // TODO get the list of media when update mode
        List<Bitmap> bitmaps = new ArrayList<>();
        if(!media.isEmpty()) {
            for (String m : media) {
                String directoryName = m.split("/")[0];
                String fileName = m.split("/")[1];
                ContextWrapper contextWrapper = new ContextWrapper(this.getActivity().getApplicationContext());
                File directory = contextWrapper.getDir(directoryName, Context.MODE_PRIVATE);
                File file = new File(directory, fileName + this.FILE_EXTENTION);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                bitmaps.add(bitmap);
            }
            this.photosAdapter.updateList(bitmaps);
        }
        return root;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_form_camera) {
            this.launcherRequestPermissionForCamera.launch(this.PERMISSION_CAMERA);

        } else if(view.getId() == R.id.button_save_form) {
            this.launcherRequestPermissionToWriteStorage.launch(this.PERMISSION_WRITE_STORAGE);
            this.next();

        } else if(view.getId() == R.id.button_skip_form) {
            this.next();
        }
    }

    @Override
    public void save() {

        if(!this.photos.isEmpty()) {
            List<String> paths = new ArrayList<>();
            // save photos in storage and get the path
            for(Bitmap photo: this.photos) {
                String path = this.saveImageInStorage(photo);
                paths.add(path);
            }
            // Save file paths in db
            this.handleMediaData.setEstateMediaData(paths);
            this.saveEstateDataUpdate.saveEstateDataUpdate();
        }
    }

    private File createFileWithRandomName() {
        // Create the file name for the photo
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fileName = this.FILE_NAME_PREFIX + n + this.FILE_EXTENTION;

        // Create the path
        File targetDirectory = new File(this.STORAGE_ROOT_PATH + "/" + this.STORAGE_DIRECTORY_NAME);
        targetDirectory.mkdirs();

        // Create the photo file in storage
        File file = new File(targetDirectory, fileName);
        return file;
    }

    private String saveImageInStorage(Bitmap bitmap) {
        // https://stackoverflow.com/questions/7887078/android-saving-file-to-external-storage
        File file = this.createFileWithRandomName();
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        //D/MediaScannerConnection: Scanned /storage/emulated/0/Pictures/rem_saved_images/rem_9359.jpg to content://media/external_primary/images/media/75
        MediaScannerConnection.scanFile(this.getContext(), new String[] { file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                        //I/ExternalStorage: Scanned /storage/emulated/0/Pictures/rem_saved_images/rem_9359.jpg:
                        //I/ExternalStorage: -> uri=content://media/external_primary/images/media/75
                    }
                });

        return file.getAbsolutePath();
    }

    @Override
    public FormStepEnum getCurrentStep() {
        return FormStepEnum.MEDIA;
    }

    interface HandleMediaData {
        void setEstateMediaData(List<String> media);
    }
}
