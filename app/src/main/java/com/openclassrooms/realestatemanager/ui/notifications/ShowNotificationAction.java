package com.openclassrooms.realestatemanager.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

public class ShowNotificationAction {

    private ConfigureNotification configureNotification;

    private NotificationManagerCompat notificationManager;

    private int notificationId;

    public ShowNotificationAction(Context context) {
        this.notificationManager = NotificationManagerCompat.from(context);
        this.configureNotification = new ConfigureNotification(context, this.notificationManager);
    }

    public void show() {
        Notification notification = this.configureNotification.build(this.notificationId);
        this.notificationManager.notify(this.notificationId, notification);
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
