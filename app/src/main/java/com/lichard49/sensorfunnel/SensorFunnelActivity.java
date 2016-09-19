package com.lichard49.sensorfunnel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SensorFunnelActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_funnel);

        Intent startService = new Intent(SensorFunnelActivity.this, SensorFunnelService.class);
        startService(startService);

        Button startButton = (Button) findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SensorFunnelService.getInstance().isReadingData()) {
                    SensorFunnelService.getInstance().stopReadingData();
                } else {
                    SensorFunnelService.getInstance().startReadingData();
                }
            }
        });

        tv = (TextView) findViewById(R.id.textView);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String data = intent.getStringExtra(SensorFunnelService.UPDATE_DATA);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(data);
                    }
                });
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(SensorFunnelService.UPDATE_REQUEST));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
