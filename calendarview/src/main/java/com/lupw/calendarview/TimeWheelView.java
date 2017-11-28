package com.lupw.calendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.lupw.calendarview.view_wheel.StringScrollPicker;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lupengwei on 2017/11/27.
 * Admin Lupw
 */

public class TimeWheelView extends LinearLayout {
    private StringScrollPicker hourView;
    private StringScrollPicker minView;
    private StringScrollPicker secView;
    public enum Mode {HOUR_MIN_SEC, HOUR_MIN}

    private static final String[] HOURS = {"00时", "01时", "02时", "03时", "04时", "05时", "06时", "07时", "08时", "09时",
            "10时", "11时", "12时", "13时", "14时", "15时", "16时", "17时", "18时", "19时",
            "20时", "21时", "22时", "23时"};

    private static final String[] MINS = {"00分", "01分", "02分", "03分", "04分", "05分", "06分", "07分", "08分", "09分",
            "10分", "11分", "12分", "13分", "14分", "15分", "16分", "17分", "18分", "19分",
            "20分", "21分", "22分", "23分", "24分", "25分", "26分", "27分", "28分", "29分",
            "30分", "31分", "32分", "33分", "34分", "35分", "36分", "37分", "38分", "39分",
            "40分", "41分", "42分", "43分", "44分", "45分", "46分", "47分", "48分", "49分",
            "50分", "51分", "52分", "53分", "54分", "55分", "56分", "57分", "58分", "59分"};

    private static final String[] SECS = {"00秒", "01秒", "02秒", "03秒", "04秒", "05秒", "06秒", "07秒", "08秒", "09秒",
            "10秒", "11秒", "12秒", "13秒", "14秒", "15秒", "16秒", "17秒", "18秒", "19秒",
            "20秒", "21秒", "22秒", "23秒", "24秒", "25秒", "26秒", "27秒", "28秒", "29秒",
            "30秒", "31秒", "32秒", "33秒", "34秒", "35秒", "36秒", "37秒", "38秒", "39秒",
            "40秒", "41秒", "42秒", "43秒", "44秒", "45秒", "46秒", "47秒", "48秒", "49秒",
            "50秒", "51秒", "52秒", "53秒", "54秒", "55秒", "56秒", "57秒", "58秒", "59秒"};


    public TimeWheelView(Context context) {
        super(context);
        init(context);
    }


    public TimeWheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public TimeWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @SuppressLint("InflateParams")
    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_wheel_view, null, false);
        hourView = (StringScrollPicker) view.findViewById(R.id.hourView);
        minView = (StringScrollPicker) view.findViewById(R.id.minView);
        secView = (StringScrollPicker) view.findViewById(R.id.secView);
        hourView.setData(new ArrayList<CharSequence>(Arrays.asList(HOURS)));
        minView.setData(new ArrayList<CharSequence>(Arrays.asList(MINS)));
        secView.setData(new ArrayList<CharSequence>(Arrays.asList(SECS)));

        addView(view);
    }


    /**
     * 设置TimePiker的模式
     *
     * @param mode TimePicker的模式
     */
    public void setMode(Mode mode) {
        switch (mode) {
            case HOUR_MIN_SEC:
                hourView.setVisibility(VISIBLE);
                minView.setVisibility(VISIBLE);
                secView.setVisibility(VISIBLE);
                break;
            case HOUR_MIN:
                hourView.setVisibility(VISIBLE);
                minView.setVisibility(VISIBLE);
                secView.setVisibility(GONE);
                break;
            default:
                break;
        }
    }


    /**
     * 设置时间选择器默认的时间
     *
     * @param hour 时
     * @param min  分
     * @param sec  秒
     */
    public void setTime(String hour, String min, String sec) {
        String timeFiled, tempTimeFiled;
        int i;

        timeFiled = hour + "时";
        for (i = 0; i < HOURS.length; i ++) {
            tempTimeFiled = HOURS[i];
            if (tempTimeFiled.equals(timeFiled)) {
                break;
            }
        }
        hourView.setSelectedPosition(i);

        timeFiled = min + "分";
        for (i = 0; i < MINS.length; i ++) {
            tempTimeFiled = MINS[i];
            if (tempTimeFiled.equals(timeFiled)) {
                break;
            }
        }
        minView.setSelectedPosition(i);

        timeFiled = sec + "秒";
        for (i = 0; i < SECS.length; i ++) {
            tempTimeFiled = SECS[i];
            if (tempTimeFiled.equals(timeFiled)) {
                break;
            }
        }
        secView.setSelectedPosition(i);
    }


    public String getHour() {
        int i = hourView.getSelectedPosition();
        return HOURS[i].replace("时", "");
    }


    public String getMin() {
        int i = minView.getSelectedPosition();
        return MINS[i].replace("分", "");
    }


    public String getSec() {
        int i = secView.getSelectedPosition();
        return SECS[i].replace("秒", "");
    }
}
