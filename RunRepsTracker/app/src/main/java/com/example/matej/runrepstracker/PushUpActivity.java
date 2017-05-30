package com.example.matej.runrepstracker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PushUpActivity extends AppCompatActivity implements SensorEventListener {
    TextView PushUpTv, PushUpCountTv;
    Button ResetBtn,SaveBtn;
    SensorManager sensorManager;
    Sensor ProximitySensor;
    int PushUpCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        setUpUI();
    }

    private void setUpUI(){
            PushUpTv = (TextView) findViewById(R.id.PushUpTv);
            ResetBtn = (Button) findViewById(R.id.ResetBtn);
            SaveBtn = (Button) findViewById(R.id.SaveBtn);
        PushUpCountTv = (TextView) findViewById(R.id.PushUpCountTv);

        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushUpCounter = 0;
                PushUpCountTv.setText(String.valueOf(PushUpCounter));
            }
        });
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        float distance = ProximitySensor.getMaximumRange();
           if (event.values[0] < distance) {
               PushUpCounter++;
            PushUpCountTv.setText(String.valueOf(PushUpCounter));

        }
        }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, ProximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    }


