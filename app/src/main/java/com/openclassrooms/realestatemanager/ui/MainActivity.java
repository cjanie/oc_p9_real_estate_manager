package com.openclassrooms.realestatemanager.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.utils.Utils;

public class MainActivity extends BaseActivity {

    private TextView textViewMain;
    private TextView textViewQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main); // id of text view was incorrect
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        this.configureTextViewMain();
        this.configureTextViewQuantity();

        this.textViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        this.makeSimpleNotification();
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        String quantity = String.valueOf(Utils.convertDollarToEuro(100)); // String instead of int
        this.textViewQuantity.setTextSize(20);
        this.textViewQuantity.setText(quantity);
    }

    // Notification
    // https://medium.com/@myofficework000/android-notification-in-koltin-7a81f6b766bb
    private NotificationChannel notificationChannel;
    private final int smallIconId = R.drawable.ic_baseline_location_city_24;

    private void makeSimpleNotification() {
        NotificationManager notificationManager = notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        this.initNotificationChannel(notificationManager);

        Notification.Builder notificationBuilder = new Notification.Builder(this, this.getChannelId())
                .setContentTitle(this.getNotificationTitle())
                .setContentText(this.getNotificationContent())
                .setSmallIcon(smallIconId)
                .setContentIntent(this.setPendingIntent(this));

        notificationManager.notify(1, notificationBuilder.build());
    }

    private void initNotificationChannel(NotificationManager notificationManager) {
        this.notificationChannel = new NotificationChannel(this.getChannelId(), this.getChannelDescription(), NotificationManager.IMPORTANCE_HIGH);
        this.notificationChannel.enableLights(true);
        this.notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(this.notificationChannel);
    }

    private String getNotificationTitle() {
        return this.getString(R.string.app_name);
    }

    private String getNotificationContent() {
        return this.getString(R.string.notification_content);
    }

    private String getChannelId() {
        return "Notification Channel" + this.getString(R.string.app_name);
    }

    private String getChannelDescription() {
        return this.getString(R.string.notification_channel_description);
    }


    private PendingIntent setPendingIntent(Context context) {
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }


}
