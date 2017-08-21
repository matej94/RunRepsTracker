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

    TextView SitUpTextTv,SetTextTv, SitUpCountTv,SetCountTv,RepsTv;
    Button ResetWorkoutBtn, SaveWorkoutBtn,SaveSetBtn;
    SensorManager sensorManager;
    Sensor AccelerometerSensor;
    int i = 0;
    String [] reps = new String[1];
    int SitUpCounter = 0;
    int SetCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_up);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        AccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        setUpUI();
    }

    private void setUpUI(){
        RepsTv = (TextView) findViewById(R.id.RepsTv);
        SitUpTextTv = (TextView) findViewById(R.id.SitUpTextTv);
        SetTextTv = (TextView) findViewById(R.id.SetTextTv);
        ResetWorkoutBtn = (Button) findViewById(R.id.ResetWorkoutBtn);
        SaveWorkoutBtn = (Button) findViewById(R.id.SaveWorkoutBtn);
        SaveSetBtn = (Button) findViewById(R.id.SaveSetBtn);
        SitUpCountTv = (TextView) findViewById(R.id.SitUpCountTv);
        SetCountTv = (TextView) findViewById(R.id.SetCountTv);

        ResetWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SitUpCounter = 0;
                SitUpCountTv.setText(String.valueOf(SitUpCounter));
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
                reps[i]=SitUpCountTv.getText().toString();
                RepsTv.append(reps[i]+ "   ");
                SitUpCounter = 0;
                SitUpCountTv.setText(String.valueOf(SitUpCounter));

            }
        });

        SaveWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSitUp,sSets;

                sSitUp = RepsTv.getText().toString();
                sSets = SetCountTv.getText().toString();

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
