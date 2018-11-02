package com.ceribit.android.basicnotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static String CHANNEL_ID = "channel";
    private static int NOTIFICATION_ID = 101;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Receiver", "Called");
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 10,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(context.getString(R.string.notification_single_title))
                .setContentText(context.getText(R.string.notification_interval_description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1, mBuilder.build());
    }
}
