package com.lichard49.sensorfunnel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by richard on 8/14/16.
 */
public class SensorFunnelService extends Service {

    private final int NOTIFICATION_ID = 169;

    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("lichard49", "Service started!");
        showNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("lichard49", "Service destroyed!");
        hideNotification();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // used only for bound services
        return null;
    }

    private void showNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Sensor Funnel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .build();
        notificationManager.notify(NOTIFICATION_ID, n);
    }

    private void hideNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
