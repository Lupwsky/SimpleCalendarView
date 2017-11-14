package com.lupw.simplecalendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.lupw.calendarview.listener.OnSelectListener;
import com.lupw.calendarview.view.CalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnCalenderSelectListener(new OnSelectListener() {
            @Override
            public void selected(String date, boolean isDay) {
                Log.e("Caneldar", date + " : " + isDay);
            }
        });
        calendarView.create();
    }
}
