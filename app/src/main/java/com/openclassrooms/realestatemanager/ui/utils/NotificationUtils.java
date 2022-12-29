package com.openclassrooms.realestatemanager.ui.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.ui.MainActivity;

public class NotificationUtils {


    // https://medium.com/@myofficework000/android-notification-in-koltin-7a81f6b766bb
    private Context context;
    private Class packageContextClass;
    private NotificationChannel notificationChannel;
    private final int smallIconId = R.drawable.ic_baseline_location_city_24;

    public NotificationUtils(Context context, Class packageContextClass) {
        this.context = context;
        this.packageContextClass = packageContextClass;
    }

    public void makeNotification(Estate estate) {
        String estateId = String.valueOf(estate.getId());

        if(!estate.getMediaList().isEmpty()) {
            this.makeBigPictureNotification(estateId, estate.getMediaList().get(0).getPath());
        } else {
            if(estate.getStreetNumberAndStreetName() != null && estate.getLocation() != null) {
                this.makeInboxStyleNotification(estateId, estate.getStreetNumberAndStreetName(), estate.getLocation());
            } else {
                this.makeSimpleNotification(estateId);
            }
        }
    }

    private void makeSimpleNotification(String estateId) {
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);

        this.initNotificationChannel(notificationManager);

        notificationManager.notify(Integer.parseInt(estateId), this.getNotificationBuilder(estateId).build());
    }

    private void makeBigPictureNotification(String estateId, String picturePath) {
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);

        this.initNotificationChannel(notificationManager);

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(BitmapFactory.decodeFile(picturePath));

        NotificationCompat.Builder notificationBuilder = this.getNotificationBuilder(estateId);
        notificationBuilder.setStyle(style);
        notificationManager.notify(Integer.parseInt(estateId), notificationBuilder.build());
    }

    private void makeInboxStyleNotification(String estateId, String address, String location) {
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);

        this.initNotificationChannel(notificationManager);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.addLine(address);
        style.addLine(location);

        NotificationCompat.Builder notificationBuilder = this.getNotificationBuilder(estateId);
        notificationBuilder.setStyle(style);
        notificationManager.notify(Integer.parseInt(estateId), notificationBuilder.build());
    }

    private void initNotificationChannel(NotificationManager notificationManager) {
        this.notificationChannel = new NotificationChannel(this.getChannelId(), this.getChannelDescription(), NotificationManager.IMPORTANCE_HIGH);
        this.notificationChannel.enableLights(true);
        this.notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(this.notificationChannel);
    }

    private NotificationCompat.Builder getNotificationBuilder(String estateId) {
        return new NotificationCompat.Builder(this.context, this.getChannelId())
                .setContentTitle(this.getNotificationTitle())
                .setContentText(this.getNotificationContent(estateId))
                .setSmallIcon(this.smallIconId)
                .setContentIntent(this.createPendingIntent());
    }

    private String getNotificationTitle() {
        return this.context.getString(R.string.app_name);
    }

    private String getNotificationContent(String estateId) {
        return this.context.getString(R.string.notification_content) + " " + estateId;
    }

    private String getChannelId() {
        return "Notification Channel" + this.context.getString(R.string.app_name);
    }

    private String getChannelDescription() {
        return this.context.getString(R.string.notification_channel_description);
    }

    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(this.context, this.packageContextClass);
        return PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

}
