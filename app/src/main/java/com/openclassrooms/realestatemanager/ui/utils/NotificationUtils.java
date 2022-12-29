package com.openclassrooms.realestatemanager.ui.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

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
        this.makeSimpleNotification(estate.getId());
    }

    private void makeSimpleNotification(long estateId) {
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);

        this.initNotificationChannel(notificationManager);

        Notification.Builder notificationBuilder = new Notification.Builder(this.context, this.getChannelId())
                .setContentTitle(this.getNotificationTitle())
                .setContentText(this.getNotificationContent(estateId))
                .setSmallIcon(smallIconId)
                .setContentIntent(this.createPendingIntent());

        notificationManager.notify(1, notificationBuilder.build());
    }

    private void initNotificationChannel(NotificationManager notificationManager) {
        this.notificationChannel = new NotificationChannel(this.getChannelId(), this.getChannelDescription(), NotificationManager.IMPORTANCE_HIGH);
        this.notificationChannel.enableLights(true);
        this.notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(this.notificationChannel);
    }

    private String getNotificationTitle() {
        return this.context.getString(R.string.app_name);
    }

    private String getNotificationContent(long estateId) {
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
