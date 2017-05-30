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

public class SitUpActivity extends AppCompatActivity implements SensorEventListener {
    TextView SitUpTv, SitUpCountTv, textX, textY, textZ;
    Button ResetBtn,SaveBtn;
    SensorManager sensorManager;
    Sensor AccelerometerSensor;
    int SitUpCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_up);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        AccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        setUpUI();
    }

    private void setUpUI(){
        SitUpTv = (TextView) findViewById(R.id.SitUpTv);
        SitUpCountTv = (TextView) findViewById(R.id.SitUpCountTv);
        ResetBtn = (Button) findViewById(R.id.ResetBtn);
        SaveBtn = (Button) findViewById(R.id.SaveBtn);
        textX = (TextView) findViewById(R.id.textx);
        textY = (TextView) findViewById(R.id.texty);
        textZ = (TextView) findViewById(R.id.textz);
        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SitUpCounter = 0;
                SitUpCountTv.setText(String.valueOf(SitUpCounter));
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        int xi = Math.round(x);
        int yi = Math.round(y);
        int zi = Math.round(z);

        textX.setText(String.valueOf(xi));
        textY.setText(String.valueOf(yi));
        textZ.setText(String.valueOf(zi));

        if (zi > 6 && zi < 9) {
            SitUpCounter++;
            SitUpCountTv.setText(String.valueOf(SitUpCounter));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, AccelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
