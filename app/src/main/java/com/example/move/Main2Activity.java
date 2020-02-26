package com.example.move;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Objects;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class Main2Activity extends AppCompatActivity {

    SensorManager sensorManager;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    boolean running = false;

    TextView countdownText;
    TextView testTextView;
    Button btnAbbrechen;
    Button btnPause;
    String minute;
    boolean timerRunning;
    CountDownTimer countDownTimer;
    long timeLeftInMilliseconds;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        setContentView(R.layout.activity_main2);
        countdownText=(TextView) findViewById(R.id.textView);

        testTextView=(TextView) findViewById(R.id.textView5);

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
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        countDownTimer.cancel();
        btnPause.setText("Weiter");
        timerRunning = false;
        vibrator.cancel();
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

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            if(timerRunning == false){
                vibrator.cancel();
            } else {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta;
                if (mAccel > 12) {
                    vibrator.cancel();
                    testTimer();
                } else {
                    long[] mVibratePattern = new long[]{0, 200, 200, 200};
                    vibrator.vibrate(mVibratePattern, -1);
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    public void testTimer() {
        mSensorManager.unregisterListener(mSensorListener);
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        TimerTask task = new TimerTask() {
            public void run() {
                vibrator.cancel();
                mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_NORMAL);
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 3000L;
        timer.schedule(task, delay);
    }

}
