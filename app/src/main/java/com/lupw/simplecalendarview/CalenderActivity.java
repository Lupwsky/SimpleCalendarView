package com.lupw.simplecalendarview;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.lupw.calendarview.DialogCalendarView;
import com.lupw.calendarview.PopupWindowCalendar;
import com.lupw.simplecalendarview.databinding.ActivityCalenderBinding;

public class CalenderActivity extends AppCompatActivity {
    private ActivityCalenderBinding binding;
    private PopupWindowCalendar popupWindowCalendar;
    private String currDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.calendarView.setOnCalenderSelectListener((date, isDay) -> Log.e("Caneldar", date + " : " + isDay));
        binding.calendarView.create();

        binding.btnOpen.setOnClickListener(v -> {
            DialogCalendarView dialogCalendarView = DialogCalendarView.getInstance(currDate);
            dialogCalendarView.setOnSelectedListener((date, isDayMode) -> {
                Log.e("calendar", date);
                currDate = date;
            });
            dialogCalendarView.show(getSupportFragmentManager(), "calendar");
        });

        binding.txtOpenCalender.setOnClickListener(v -> {
            if (popupWindowCalendar == null) {
                popupWindowCalendar = new PopupWindowCalendar(this, binding.llDefToolBar);
                popupWindowCalendar.setOnDateSelectListener((strDate, isDay) ->
                        Log.e("popupwindow", "mode : " + isDay + ", " + "date : " + strDate));
            } else {
                popupWindowCalendar.show();
            }
        });
    }
}
