package com.example.move;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
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
            }
        });

        btnPause=(Button)findViewById(R.id.btnStart);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
                vibrator.vibrate(100);
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
}
