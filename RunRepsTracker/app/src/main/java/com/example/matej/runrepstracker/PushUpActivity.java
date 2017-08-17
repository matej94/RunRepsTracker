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
import android.widget.ListView;
import android.widget.TextView;

public class PushUpActivity extends AppCompatActivity implements SensorEventListener {
    public static final String KEY_PUSHUP = "input pushup";
    public static final String KEY_SETS = "input sets";


    TextView PushUpTextTv,SetTextTv, PushUpCountTv,SetCountTv;
    Button ResetSetBtn,ResetPushUpBtn, SaveWorkoutBtn,SaveSetBtn;
    SensorManager sensorManager;
    Sensor ProximitySensor;
    int PushUpCounter = 0;
    int SetCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        setUpUI();
    }
        private void setUpUI(){
            PushUpTextTv = (TextView) findViewById(R.id.PushUpTextTv);
            SetTextTv = (TextView) findViewById(R.id.SetTextTv);
            ResetPushUpBtn = (Button) findViewById(R.id.ResetPushUpBtn);
            ResetSetBtn = (Button) findViewById(R.id.ResetSetBtn);
            SaveWorkoutBtn = (Button) findViewById(R.id.SaveWorkoutBtn);
            SaveSetBtn = (Button) findViewById(R.id.SaveSetBtn);
            PushUpCountTv = (TextView) findViewById(R.id.PushUpCountTv);
            SetCountTv = (TextView) findViewById(R.id.SetCountTv);
            ResetPushUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushUpCounter = 0;
                PushUpCountTv.setText(String.valueOf(PushUpCounter));
            }
        });
            ResetSetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetCounter = 0;
                    SetCountTv.setText(String.valueOf(SetCounter));
                }
            });
            SaveSetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetCounter++;
                    SetCountTv.setText(String.valueOf(SetCounter));

                }
            });
        SaveWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPushUp = null, sSets = null;

                    sPushUp = PushUpCountTv.getText().toString();
                    sSets = SetCountTv.getText().toString();

                    Intent explicitIntent = new Intent();

                    explicitIntent.putExtra(KEY_PUSHUP, sPushUp);
                    explicitIntent.putExtra(KEY_SETS, sSets);
                    explicitIntent.setClass(getApplicationContext(), PushUpHistoryActivity.class);
                    startActivity(explicitIntent);
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


