package com.openclassrooms.realestatemanager.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
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
    private NotificationManager notificationManager;
    private Notification.Builder notificationBuilder;
    private final String channelId = "ChannelId";
    private final String channelDescription = "ChannelDescription";
    private String notificationTitle;//this.getString(R.string.app_name);
    private String notificationContent;//this.getString(R.string.notification_saved);
    private int smallIconId = R.drawable.ic_baseline_location_city_24;

    private void makeSimpleNotification() {
        this.getNotificationChannel();

        this.notificationBuilder = new Notification.Builder(this, this.channelId)
                .setContentTitle(this.notificationTitle)
                .setContentText(this.notificationContent)
                .setSmallIcon(smallIconId)
                .setContentIntent(this.setPendingIntent(this));

        this.notificationManager.notify(1, this.notificationBuilder.build());
    }

    private void getNotificationChannel() {
        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        this.notificationChannel = new NotificationChannel(this.channelId, this.channelDescription, NotificationManager.IMPORTANCE_HIGH);
        this.notificationChannel.enableLights(true);
        this.notificationChannel.enableVibration(true);
        this.notificationManager.createNotificationChannel(this.notificationChannel);
    }



    private PendingIntent setPendingIntent(Context context) {
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }


}
