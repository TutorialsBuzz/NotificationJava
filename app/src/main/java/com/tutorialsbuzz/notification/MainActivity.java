package com.tutorialsbuzz.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        Button politics, sports, entertainment;

        politics = findViewById(R.id.politics);
        sports = findViewById(R.id.sports);
        entertainment = findViewById(R.id.entertainment);

        politics.setOnClickListener(this);
        sports.setOnClickListener(this);
        entertainment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.politics:
                // politics politics
                createNotification(
                        getResources().getString(R.string.channel_politics),
                        getResources().getString(R.string.politics_content),
                        getResources().getString(R.string.channel_politics),
                        NotificationCompat.PRIORITY_HIGH,
                        100
                );
                break;

            case R.id.sports:
                //Sports politics
                createNotification(
                        getResources().getString(R.string.channel_sports),
                        getResources().getString(R.string.sports_content),
                        getResources().getString(R.string.channel_sports),
                        NotificationCompat.PRIORITY_DEFAULT,
                        101
                );
                break;

            case R.id.entertainment:
                //Entertainment Notification
                createNotification(
                        getResources().getString(R.string.channel_entertainment),
                        getResources().getString(R.string.entertainment_content),
                        getResources().getString(R.string.channel_entertainment),
                        NotificationCompat.PRIORITY_LOW,
                        102
                );

                break;
        }
    }

    /**
     * Create Notification
     * Param
     * 1. title
     * 2. content
     * 3. channelId
     * 4.priorty
     * 5. notificationId
     */

    private void createNotification(String title, String content,
                                    String channedId, int priorty, int notificationID) {


        Intent intent = new Intent(this, NotifyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Bundle extras = new Bundle();
        extras.putString(NotifyActivity.notify_title, title);
        extras.putString(NotifyActivity.notify_content, content);
        intent.putExtras(extras);

        intent.setAction(Intent.ACTION_VIEW);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), notificationID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), channedId)
                        .setSmallIcon(R.drawable.ic_notifications_active)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .setLights(Color.BLUE, 500, 500)
                        .setVibrate(new long[]{500, 500, 500})
                        .setPriority(priorty)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


        // Since android Oreo notification channel is needed.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channedId,
                    channedId,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = notificationBuilder.build();

        notificationManager.notify(notificationID, notification);

        playNotificationSound();
    }

    public void playNotificationSound() {
        try {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(MainActivity.this, defaultSoundUri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}