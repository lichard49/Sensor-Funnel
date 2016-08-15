package com.lichard49.sensorfunnel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by richard on 8/14/16.
 */
public class SensorFunnelService extends Service {

    private static SensorFunnelService serviceInstance;

    private final int NOTIFICATION_ID = 169;

    private NotificationManager notificationManager;
    private Notification.Builder notificationBuilder;

    private boolean isReadingData = false;
    private Runnable readDataThread = new Runnable() {
        @Override
        public void run() {
            // connect to sensors
            int i = 0;

            while(isReadingData) {
                try {
                    Thread.sleep(1000);
                    updateNotificationContent(i + " finished");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            updateNotificationContent("Done");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("lichard49", "Service started!");
        serviceInstance = this;
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

    public static SensorFunnelService getInstance() {
        return serviceInstance;
    }

    private void showNotification() {
        // https://gist.github.com/kristopherjohnson/6211176
        Intent showTaskIntent = new Intent(getApplicationContext(), SensorFunnelActivity.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent startActivity = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationBuilder = new Notification.Builder(this)
                .setContentTitle("Sensor Funnel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setContentIntent(startActivity);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void hideNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void updateNotificationContent(String content) {
        notificationBuilder.setContentText(content);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public void startReadingData() {
        if(!isReadingData) {
            isReadingData = true;
            new Thread(readDataThread).start();
        }
    }

    public void stopReadingData() {
        if(isReadingData) {
            isReadingData = false;
        }
    }

    public boolean isReadingData() {
        return isReadingData;
    }
}
