package com.ceribit.android.basicnotifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static String CHANNEL_ID = "channel";
    private static int NOTIFICATION_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void btnSingleClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.notification_single_title))
                .setContentText(getString(R.string.notification_single_description))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_single_description)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public void btnIntervalClicked(View view){
        Log.e("btnInterval", "Clicked");
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        System.out.println("calling Alarm receiver ");
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //set the notification to repeat every fifteen minutes
        long startTime = 1*60*1000; // 2 min
        // set unique id to the pending item, so we can call it when needed
        PendingIntent pi = PendingIntent.getBroadcast(this, 101342, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        //alarmManager.cancel(pi);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
                60*1000, pi);
    }

    public void stopBtnClicked(View view){
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(this, 101342, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pi);

    }
}
