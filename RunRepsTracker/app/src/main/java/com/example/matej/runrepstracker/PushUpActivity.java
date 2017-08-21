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

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class PushUpActivity extends AppCompatActivity implements SensorEventListener {
    public static final String KEY_PUSHUP = "input pushup";
    public static final String KEY_SETS = "input sets";


    TextView PushUpTextTv,SetTextTv, PushUpCountTv,SetCountTv,RepsTv;
    Button ResetWorkoutBtn, SaveWorkoutBtn,SaveSetBtn;
    SensorManager sensorManager;
    Sensor ProximitySensor;
    int i = 0;
    int PushUpCounter = 0;
    int SetCounter = 0;
    String [] reps = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        setUpUI();
    }
        private void setUpUI(){
            RepsTv = (TextView) findViewById(R.id.RepsTv);
            PushUpTextTv = (TextView) findViewById(R.id.PushUpTextTv);
            SetTextTv = (TextView) findViewById(R.id.SetTextTv);
            ResetWorkoutBtn = (Button) findViewById(R.id.ResetWorkoutBtn);
            SaveWorkoutBtn = (Button) findViewById(R.id.SaveWorkoutBtn);
            SaveSetBtn = (Button) findViewById(R.id.SaveSetBtn);
            PushUpCountTv = (TextView) findViewById(R.id.PushUpCountTv);
            SetCountTv = (TextView) findViewById(R.id.SetCountTv);
            ResetWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushUpCounter = 0;
                PushUpCountTv.setText(String.valueOf(PushUpCounter));
                SetCounter = 0;
                SetCountTv.setText(String.valueOf(SetCounter));
                RepsTv.setText("");

            }
        });

            SaveSetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetCounter++;
                    SetCountTv.setText(String.valueOf(SetCounter));
                    reps[i]=PushUpCountTv.getText().toString();
                    RepsTv.append(reps[i]+ "   ");
                    PushUpCounter = 0;
                    PushUpCountTv.setText(String.valueOf(PushUpCounter));

                }
            });

            SaveWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPushUp, sSets;

                    sPushUp = RepsTv.getText().toString();
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


