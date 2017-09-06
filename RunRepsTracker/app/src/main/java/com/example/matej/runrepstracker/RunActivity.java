package com.example.matej.runrepstracker;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.List;

public class RunActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    public static final String KEY_TIME = "input time";
    public static final String KEY_DIST = "input dist";
    private Button mStartButton;
    private Button mStopButton;
    private Button mResetButton;
    private Button mSaveButton;
    private Chronometer mChronometer;
    private TextView distanceTv;

    private long lastPause;
    private GoogleMap map;
    private Polyline gpsTrack;
    private SupportMapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private LatLng lastKnownLatLng;
    Marker mCurrLocationMarker;
    Location mLastLocation;
    double cLat,cLng,pLat,pLng;
    double distance = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        distanceTv = (TextView) findViewById(R.id.distanceTv);
        mStartButton = (Button) findViewById(R.id.StartBtn);
        mStopButton = (Button) findViewById(R.id.StopBtn);
        mResetButton = (Button) findViewById(R.id.ResetBtn);
        mSaveButton = (Button) findViewById(R.id.SaveBtn);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);


        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPause != 0){
                    mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                }
                else{
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                }

                mChronometer.start();
                mStartButton.setEnabled(false);
                mStopButton.setEnabled(true);


            }
        });
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPause = SystemClock.elapsedRealtime();
                mChronometer.stop();
                mStopButton.setEnabled(false);
                mStartButton.setEnabled(true);
            }
        });
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.stop();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
                mStartButton.setEnabled(true);
                mStopButton.setEnabled(false);

            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTime, sDist;

                sTime = mChronometer.getText().toString();
                sDist = distanceTv.getText().toString();

                Intent explicitIntent = new Intent();

                explicitIntent.putExtra(KEY_TIME, sTime);
                explicitIntent.putExtra(KEY_DIST, sDist);
                explicitIntent.setClass(getApplicationContext(), RunHistoryActivity.class);
                startActivity(explicitIntent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(4);
        gpsTrack = map.addPolyline(polylineOptions);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        startLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
                if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                   }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        lastKnownLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        getDistance(location);
        updateTrack();
    }
    private void getDistance(Location location) {

        if (pLat == 0.0 && pLng == 0.0 ){
            pLat = location.getLatitude();
            pLng = location.getLongitude();
        }

        if (cLat == 0.0 && cLng == 0.0){
            cLat = location.getLatitude();
            cLng = location.getLongitude();
        }

        if (pLat != cLat && pLng != cLng) {
            pLat = cLat;
            pLng = cLng;
        }

        cLat = location.getLatitude();
        cLng = location.getLongitude();

        distance += getDistanceBetweenGeoPoints(cLat, cLng, pLat, pLng);
        String convertedDistance = String.format("%.2f", Math.round(distance * 100) / 100.0);
        distanceTv.setText(" " + convertedDistance + "km");
    }
    public double getDistanceBetweenGeoPoints(Double cLat, Double cLng, Double pLat, Double pLng) {
        double earthRadius = 6367;
        double dLat = Math.toRadians(cLat - pLat);
        double dLng = Math.toRadians(cLng - pLng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(cLat)) * Math.cos(Math.toRadians(pLat)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (double) (earthRadius * c);
        return dist;
    }
    protected void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }
    private void updateTrack() {
        List<LatLng> points = gpsTrack.getPoints();
        points.add(lastKnownLatLng);
        gpsTrack.setPoints(points);
    }

}