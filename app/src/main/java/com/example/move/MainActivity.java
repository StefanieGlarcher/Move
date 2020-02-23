package com.example.move;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    TimePicker picker;
    Button btnStart;
    Button btnAbbrechen;
    int hour, minute;
    String minuteText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picker=(TimePicker)findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();
                }
                else{
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }

                Timer timer = new Timer();
                minuteText = timer.setTimer(hour, minute);

                openActivity2();
            }
        });

        btnAbbrechen=(Button)findViewById(R.id.btnAbbrechen);
        btnAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setHour(0);
                picker.setMinute(0);

                hour = 0;
                minute = 0;
            }
        });
    }

    public void openActivity2(){
        Intent intent = new Intent(this, Main2Activity.class);

        Bundle bundle = new Bundle();

        bundle.putString("minute", minuteText);

        intent.putExtras(bundle);

        startActivity(intent);
    }
}
