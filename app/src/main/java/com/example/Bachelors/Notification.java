package com.example.Bachelors;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Notification extends AppCompatActivity {

    Button notif_button;
    private static final String CHANNEL_ID = "Bach";
    private static final String CHANNEL_NAME = "Bach";
    private static final String CHANNEL_DESC = "Bach";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notif_builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        notif_builder.setSmallIcon(R.drawable.ic_notification);
        notif_builder.setContentTitle("Bachelors: New notification");
        notif_builder.setContentText("New request for Property.");
        notif_builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notif_builder.build());

    }
}

