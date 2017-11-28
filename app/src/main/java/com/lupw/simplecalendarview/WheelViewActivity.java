package com.lupw.simplecalendarview;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.lupw.calendarview.DialogTimeWheelView;
import com.lupw.calendarview.TimeWheelView;
import com.lupw.calendarview.listener.OnTimePickerListener;
import com.lupw.simplecalendarview.databinding.ActivityWheelViewBinding;

public class WheelViewActivity extends AppCompatActivity {
    private DialogTimeWheelView dialogTimeWheelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWheelViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_wheel_view);
        binding.btnOpenDialog.setOnClickListener(v -> {
            if (dialogTimeWheelView == null) {
                dialogTimeWheelView = DialogTimeWheelView.getInstance("12", "12", "10", TimeWheelView.Mode.HOUR_MIN);
                dialogTimeWheelView.setOnTimePickerListener(new OnTimePickerListener() {
                    @Override
                    public void selected(String hour, String min, String sec) {
                        Log.e("time", "hour : " + hour + ", min : " + min + ", sec : " + sec);
                    }
                });
                dialogTimeWheelView.show(getSupportFragmentManager(), "time");
            } else {
                dialogTimeWheelView.show(getSupportFragmentManager(), "time");
            }
        });
    }
}
