package com.example.move;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class Timers {
    TimePicker picker;
    Button btnGet;
    TextView tvw;
    CountDownTimer countDownTimer;
    long timeleft;
    boolean timerRunning;
    Button btnPause;

    public String setTimer(int h, int m){


        int hourinM = h * 60 + m;

        String minute= Integer.toString(hourinM);

        return minute;
    }

    public void pauseTimer(){
      //  countDownTimer.cancel();
      //  timerRunning = false;
      //  startPauseButton.setText("Weiter");
    }

    public void startTimer(){
      //  countDownTimer = new CountDownTimer(timeleft, 1000);


    }

    public void checkTimer(){

    }
}
