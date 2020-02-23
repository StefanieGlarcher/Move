package com.example.move;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        timer=(TextView) findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        String minute = bundle.getString("minute");

        timer.setText(minute);

    }
}
