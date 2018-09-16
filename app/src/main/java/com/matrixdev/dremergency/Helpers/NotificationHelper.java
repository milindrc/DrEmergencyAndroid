package com.matrixdev.dremergency.Helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.matrixdev.dremergency.R;


/**
 * Created by Milind on 31-Jan-18.
 */

public class NotificationHelper {

    public static void showNotification(Context context, Intent intent, String title, String body, boolean unique) {
        try {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(title)
                    .setAutoCancel(true)
                    .setColor(context.getResources().getColor(R.color.colorAccent))
                    .setContentText(body)
                    .setSmallIcon(R.drawable.app_icon);

            if (intent != null) {
                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        101,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
//            builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));
            }

            int uniqueId = context.getSharedPreferences("pref", Context.MODE_PRIVATE).getInt("notification_counter", 0);
            final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // only for gingerbread and newer versions
                NotificationChannel mChannel = new NotificationChannel((unique ? String.valueOf(uniqueId):"-1"), "Bvicam Notification", NotificationManager.IMPORTANCE_HIGH);
                mChannel.setDescription("XXXX");
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                builder.setChannelId(mChannel.getId());
                manager.createNotificationChannel(mChannel);
            } else {
                builder.setDefaults(Notification.DEFAULT_ALL);
                builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
                builder.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            }
            manager.notify((unique ? uniqueId:-1), builder.build());
            context.getSharedPreferences("pref", Context.MODE_PRIVATE).edit().putInt("notification_counter", ++uniqueId).commit();
            Log.d("----Unique", "" + uniqueId);
        } catch (Exception e) {
            Log.d("----", e.getMessage());
        }
    }

}
