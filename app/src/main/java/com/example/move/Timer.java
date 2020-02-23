package com.example.move;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class Timer {
    TimePicker picker;
    Button btnGet;
    TextView tvw;

    public String setTimer(int h, int m){


        int hourinM = h * 60 + m;

        String minute= Integer.toString(hourinM);

        return minute;
    }

    public void pauseTimer(){
      //
    }

    public void startTimer(){
        //


    }

    public void checkTimer(){

    }
}
