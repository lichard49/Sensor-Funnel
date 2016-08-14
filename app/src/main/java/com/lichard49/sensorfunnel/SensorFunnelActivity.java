package com.lichard49.sensorfunnel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SensorFunnelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_funnel);

        Intent startService = new Intent(SensorFunnelActivity.this, SensorFunnelService.class);
        startService(startService);
    }
}
