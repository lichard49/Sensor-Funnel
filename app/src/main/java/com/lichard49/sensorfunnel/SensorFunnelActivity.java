package com.lichard49.sensorfunnel;

import android.content.Intent;
import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SensorFunnelActivity extends AppCompatActivity {

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
    }
}
