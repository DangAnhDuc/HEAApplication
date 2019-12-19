package com.example.heaapp.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.heaapp.R;
import com.example.heaapp.service.RealmService;
import com.example.heaapp.view.activity.SplashScreenActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "DailyNotificationChanelID";
    private RealmService realmService = RealmService.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, SplashScreenActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SplashScreenActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle("HEAApp Notification")
                .setContentText(context.getString(R.string.msg_daily_noti))
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notification);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        try {
            String timeStamp = String.format("%s%s%s", realmService.getCurrentDate().get(0).getYear(), realmService.getCurrentDate().get(0).getMonth(), realmService.getCurrentDate().get(0).getDate());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("remote").child("dailySummary").child(timeStamp);
            databaseReference.child("id").setValue(String.valueOf(realmService.getCurrentDate().get(0).getId()));
            databaseReference.child("date").setValue(realmService.getCurrentDate().get(0).getDate());
            databaseReference.child("month").setValue(realmService.getCurrentDate().get(0).getMonth());
            databaseReference.child("year").setValue(realmService.getCurrentDate().get(0).getYear());
            databaseReference.child("waterConsume").setValue(String.valueOf(realmService.getCurrentDate().get(0).getWaterConsume()));
            databaseReference.child("eatenCalories").setValue(String.valueOf(realmService.getCurrentDate().get(0).getEatenCalories()));
            databaseReference.child("burnedCalories").setValue(String.valueOf(realmService.getCurrentDate().get(0).getBurnedCalories()));
            databaseReference.child("neededCalories").setValue(String.valueOf(realmService.getCurrentDate().get(0).getNeededCalories()));
            databaseReference.child("eatenCarbs").setValue(String.valueOf(realmService.getCurrentDate().get(0).getEatenCarbs()));
            databaseReference.child("eatenProtein").setValue(String.valueOf(realmService.getCurrentDate().get(0).getEatenProtein()));
            databaseReference.child("eatenFat").setValue(String.valueOf(realmService.getCurrentDate().get(0).getEatenFat()));
        } catch (Exception e) {
            String timeStamp = String.format("%s%s%s", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1), String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("remote").child("dailySummary").child(timeStamp);
            databaseReference.child("id").setValue("0");
            databaseReference.child("date").setValue(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
            databaseReference.child("month").setValue(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
            databaseReference.child("year").setValue(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            databaseReference.child("waterConsume").setValue("0");
            databaseReference.child("eatenCalories").setValue("0");
            databaseReference.child("burnedCalories").setValue("0");
            databaseReference.child("neededCalories").setValue("0");
            databaseReference.child("eatenCarbs").setValue("0");
            databaseReference.child("eatenProtein").setValue("0");
            databaseReference.child("eatenFat").setValue("0");
        }
    }
}
