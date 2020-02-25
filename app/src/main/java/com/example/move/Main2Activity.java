package com.example.move;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    boolean running = false;

    //final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    final long[] pattern = {1000,1000};

    TextView countdownText;
    Button btnAbbrechen;
    Button btnPause;
    String minute;
    boolean timerRunning;
    CountDownTimer countDownTimer;
    long timeLeftInMilliseconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        setContentView(R.layout.activity_main2);
        countdownText=(TextView) findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        minute = bundle.getString("minute");

        //countdownText.setText(minute);
        timeLeftInMilliseconds = Integer.parseInt(minute) * 60000;
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        btnAbbrechen=(Button)findViewById(R.id.btnAbbrechen);
        btnAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
                vibrator.cancel();
            }
        });

        btnPause=(Button)findViewById(R.id.btnStart);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });
        startStop();
        updateTimer();
    }

    public void startStop(){
        if (timerRunning){
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
                if (timerRunning = true) {
                    // Sensor
                }
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnPause.setText("Start");
            }
        }.start();

        btnPause.setText("Pause");
        timerRunning = true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        btnPause.setText("Weiter");
        timerRunning = false;

    }

    public void updateTimer() {
        int minutes = (int) (timeLeftInMilliseconds / 1000) / 60;
        int seconds = (int) (timeLeftInMilliseconds / 1000) % 60;

        String timeLeftText =  String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownText.setText(timeLeftText);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onResume(){
       super.onResume();
       running = true;
       Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
       if(countSensor != null){
           sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
       } else {
           Toast.makeText(this, "Sensor not fount", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (running = false){
            vibrator.vibrate(pattern,0);

        } else {
           vibrator.cancel();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
