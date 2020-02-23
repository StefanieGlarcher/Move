package com.example.move;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView timer;
    Button btnAbbrechen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        timer=(TextView) findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        String minute = bundle.getString("minute");

        timer.setText(minute);

        btnAbbrechen=(Button)findViewById(R.id.btnAbbrechen);
        btnAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
