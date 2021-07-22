package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import com.example.myapplication.Model.Calendar;

public class MainActivity extends AppCompatActivity {

    public Button button,button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button1);
        button.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,MyDay.class);
            startActivity(intent);
        });
        button1=findViewById(R.id.button3);
        button1.setOnClickListener(view->{
            Intent intent1=new Intent(MainActivity.this, Month.class);
            startActivity(intent1);
        });









    }
}