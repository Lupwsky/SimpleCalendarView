package com.lupw.calendarview.view_calender_month;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import com.lupw.calendarview.view_calender_day.DayView;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

public class CalendarMonthViewPager extends ViewPager {
    private Context context;
    private CalenderMonthViewPagerAdapter calenderMonthViewPagerAdapter;

    private OnCalenderSelectListener onCalenderSelectListener;

    public CalendarMonthViewPager(Context context) {
        super(context);
        init(context, null);
    }


    public CalendarMonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        setOverScrollMode(OVER_SCROLL_NEVER);   // 设置为不能滑动时继续滑动不出现阴影效果
        setOffscreenPageLimit(0);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        if (getChildCount() > 0) {
            MonthView child = (MonthView) getChildAt(0);
            height = child.getViewHeight();
        }
        setMeasuredDimension(widthSize, height);
    }


    public void setDate(List<DateTime> dateList, DateTime startDate, DateTime endDate) {
        calenderMonthViewPagerAdapter = new CalenderMonthViewPagerAdapter(context, dateList, startDate, endDate);
        calenderMonthViewPagerAdapter.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                onCalenderSelectListener.selected(date);
            }
        });
        setAdapter(calenderMonthViewPagerAdapter);
    }


    public void setOnCalenderSelectListener(OnCalenderSelectListener onCalenderSelectListener) {
        this.onCalenderSelectListener = onCalenderSelectListener;
    }


    public void setCurrentDate(String currentDate) {
        calenderMonthViewPagerAdapter.setCurrentDate(currentDate);
    }
}
