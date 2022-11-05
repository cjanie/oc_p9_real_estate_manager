package com.openclassrooms.realestatemanager.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class StorageManager {

    private final String STORAGE_ROOT_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
    private final String STORAGE_DIRECTORY_NAME = "rem_saved_images" ;
    private final String FILE_NAME_PREFIX = "rem_";
    private final String FILE_EXTENTION = ".jpg";

    public String saveImageInStorage(Bitmap bitmap, Context context) {
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
        MediaScannerConnection.scanFile(context, new String[] { file.toString() }, null,
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
}


