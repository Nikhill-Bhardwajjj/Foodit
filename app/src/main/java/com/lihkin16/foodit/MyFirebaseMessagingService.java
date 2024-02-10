package com.lihkin16.foodit;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        FirebaseMassageRecive(message.getNotification().getTitle() ,message.getNotification().getBody());
    }

    private void FirebaseMassageRecive(String title, String body) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this ,"notify")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(body)
                .setAutoCancel(true);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(102 , builder.build());
    }
}
