package com.example.matej.runrepstracker;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SitUpActivity extends AppCompatActivity implements SensorEventListener {
    public static final String KEY_SITUP = "input situp";
    public static final String KEY_SETS = "input sets";
    TextView SitUpTv, SitUpCountTv;
    EditText SetNumberEt;
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
        SetNumberEt = (EditText) findViewById(R.id.SetNumberEt);
        ResetBtn = (Button) findViewById(R.id.ResetBtn);
        SaveBtn = (Button) findViewById(R.id.SaveBtn);

        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SitUpCounter = 0;
                SitUpCountTv.setText(String.valueOf(SitUpCounter));
            }
        });
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSitUp = null, sSets = null;

                sSitUp = SitUpCountTv.getText().toString();
                sSets = SetNumberEt.getText().toString();

                Intent explicitIntent = new Intent();

                explicitIntent.putExtra(KEY_SITUP, sSitUp);
                explicitIntent.putExtra(KEY_SETS, sSets);
                explicitIntent.setClass(getApplicationContext(), SitUpHistoryActivity.class);
                startActivity(explicitIntent);
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
