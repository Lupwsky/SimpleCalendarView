package com.lupw.simplecalendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.lupw.calendarview.listener.OnSelectListener;
import com.lupw.calendarview.view.CalendarView;
import com.lupw.calendarview.view.DialogCalendarView;

public class MainActivity extends AppCompatActivity {
    private DialogCalendarView dialogCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnCalenderSelectListener((date, isDay) -> Log.e("Caneldar", date + " : " + isDay));
        calendarView.create();

        Button btnOpen = findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(v -> {
            if (dialogCalendarView == null) {
                dialogCalendarView = DialogCalendarView.getInstance();
                dialogCalendarView.setOnSelectListener((date, isDayMode) -> {
                    Log.e("calendar", date);
                });
                dialogCalendarView.show(getSupportFragmentManager(), "calendar");
            } else {
                dialogCalendarView.show(getSupportFragmentManager(), "calendar");
            }
        });
    }
}
