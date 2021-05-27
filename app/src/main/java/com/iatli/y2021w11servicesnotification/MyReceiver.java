package com.iatli.y2021w11servicesnotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    private static String TAG ="CENG427";
    private Activity mActivity;


    public MyReceiver(Activity activity){
        mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String the_url = intent.getStringExtra("DOWNLOAD_KEY");
        Log.d(TAG, "The file in "+ the_url+ " finished");
        NotificationManager nm =
                (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("my_channel", "downloader",
                            NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(notificationChannel);
        }


        Intent resultIntent = new Intent(mActivity, MainActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mActivity);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mActivity,"my_channel" )
                .setContentTitle("Downloader Simulating App")
                .setAutoCancel(true)
                .setContentText(the_url + " downloaded")
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_file_copy_24);

        Notification notification = notificationBuilder.build();
        nm.notify(1234, notification);
    }
}