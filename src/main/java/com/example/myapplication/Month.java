package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;


public class Month extends AppCompatActivity {
    CalendarView calendarView;
    TextView date_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        calendarView = (CalendarView)findViewById(R.id.calendar);
        date_view = (TextView)findViewById(R.id.date_view);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
            date_view.setText(Date);
        });
    }
}