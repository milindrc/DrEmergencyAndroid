package com.matrixdev.dremergency.Fcm;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.matrixdev.dremergency.Controllers.Authentication.HomeActivity;
import com.matrixdev.dremergency.Helpers.NotificationHelper;
import com.matrixdev.dremergency.Utils.Constants;

/**
 * Created by Milind on 13-Jan-18.
 */

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("----", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("---", "Message data payload: " + remoteMessage.getData());

            Intent startIntent  = new Intent(this,HomeActivity.class);
            startIntent.putExtra(Constants.INTENT_HOME_TARGET, remoteMessage.getData().get("target"));

            try {
                NotificationHelper.showNotification(this, startIntent, remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), true);
            }catch (Exception e)
            {
                Log.d("----",e.getMessage());
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("----", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


}
