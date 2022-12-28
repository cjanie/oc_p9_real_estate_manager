package com.openclassrooms.realestatemanager.ui.notifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.openclassrooms.realestatemanager.Launch;

public class NotificationWorker extends Worker {

    private ShowNotificationAction showNotificationAction;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.showNotificationAction = ((Launch) context.getApplicationContext()).showNotificationAction();
    }

    @NonNull
    @Override
    public Result doWork() {

        this.showNotificationAction.show();
        return Result.success();
    }
}
