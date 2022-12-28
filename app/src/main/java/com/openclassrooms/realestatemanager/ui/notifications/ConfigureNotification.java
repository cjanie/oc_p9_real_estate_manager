package com.openclassrooms.realestatemanager.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.activity.result.ActivityResult;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.openclassrooms.realestatemanager.R;

public class ConfigureNotification {
    private final String NOTIFICATION_TEXT = "notificationText";
    private final String NOTIFICATION_ID = "notificationId";
    private final String NOTIFICATION_CHANNEL_ID = "notificationChannelId";
    private final String NOTIFICATION_CHANNEL_NAME = "notificationChannelName";

    private final Context context;


    public ConfigureNotification(Context context, NotificationManagerCompat notificationManager) {
        this.context = context;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    this.NOTIFICATION_CHANNEL_ID,
                    this.NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public Notification build(int estateId) {
        String notificationText = this.context.getString(R.string.notification_saved);
        return new NotificationCompat
                .Builder(this.context, this.NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_baseline_check_circle_outline_24_green)
                .setContentTitle(this.context.getString(R.string.app_name))
                .setContentText(notificationText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(this.createPendingIntent(estateId, notificationText))
                .setAutoCancel(true)
                .build();
    }

    private PendingIntent createPendingIntent(int notificationId, String notificationText) {
        // Create Intent and return PendingIntent
        Intent intent = new Intent(this.context, ActivityResult.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(this.NOTIFICATION_ID, notificationId);
        intent.putExtra(this.NOTIFICATION_TEXT, notificationText);

        return PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }
}
