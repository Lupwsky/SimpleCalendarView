package com.lupw.simplecalendarview;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lupw.simplecalendarview.databinding.ActivityWheelViewBinding;

public class WheelViewActivity extends AppCompatActivity {
    private int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWheelViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_wheel_view);
    }
}
