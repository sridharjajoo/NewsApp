package com.example.sridharjajoo.newsapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Create the content intent for the notification, which launches this activity
        Intent contentIntent = new Intent(context, NewsMainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "news")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("News App")
                .setContentText("Catch the latest Headlines!")
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        //Deliver the notification
        notificationManager.notify(0, builder.build());
    }
}
