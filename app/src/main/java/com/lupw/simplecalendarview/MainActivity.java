package com.lupw.simplecalendarview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.lupw.simplecalendarview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Intent intent = new Intent();
        binding.btnCalender.setOnClickListener(v -> {
            intent.setClass(MainActivity.this, CalenderActivity.class);
            startActivity(intent);
        });

        binding.btnWheelView.setOnClickListener(v -> {
            intent.setClass(MainActivity.this, WheelViewActivity.class);
            startActivity(intent);
        });
    }
}
