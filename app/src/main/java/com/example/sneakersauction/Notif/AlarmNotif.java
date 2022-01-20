package com.example.sneakersauction.Notif;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sneakersauction.R;

import java.util.Calendar;

public class AlarmNotif extends BroadcastReceiver {
    private NotificationManagerCompat notificationManager;
    MediaPlayer mMediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = NotificationManagerCompat.from(context);
        mMediaPlayer = MediaPlayer.create(context, R.raw.adhan_madina);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_expanded);

        Intent clickIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                0, clickIntent, 0);

        collapsedView.setTextViewText(R.id.text_view_collapsed_1, "Hello World!");

        expandedView.setImageViewResource(R.id.image_view_expanded, R.drawable.jordan);
        expandedView.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent);

        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_ID)

                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.jordan)
                //.setOnlyAlertOnce(true)
                .setContentTitle(context.getString(R.string.app_name))
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setVibrate(new long[]{0, 500, 1000})
                .setDefaults(Notification.DEFAULT_LIGHTS )
                .setSound(Uri.parse("android.resource://"+"com.example.tebakayatv1"+"/"+R.raw.adhan_madina))
                .build();

        notificationManager.notify(1, notification);
    }
}