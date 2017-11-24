package com.lupw.calendarview.view_calender_day;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

public class CalendarDayViewPager extends ViewPager {
    private Context context;
    private CalenderDayViewPagerAdapter calenderDayViewPagerAdapter;

    private OnCalenderSelectListener onCalenderSelectListener;

    public CalendarDayViewPager(Context context) {
        super(context);
        init(context, null);
    }


    public CalendarDayViewPager(Context context, AttributeSet attrs) {
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
            DayView child = (DayView) getChildAt(0);
            height = child.getViewHeight();
        }
        setMeasuredDimension(widthSize, height);
    }


    public void setDate(List<DateTime> dateList, DateTime startDate, DateTime endDate) {
        calenderDayViewPagerAdapter = new CalenderDayViewPagerAdapter(context, dateList, startDate, endDate);
        calenderDayViewPagerAdapter.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                onCalenderSelectListener.selected(date);
            }
        });
        setAdapter(calenderDayViewPagerAdapter);
    }


    public void setOnCalenderSelectListener(OnCalenderSelectListener onCalenderSelectListener) {
        this.onCalenderSelectListener = onCalenderSelectListener;
    }


    public void setCurrentDate(String currentDate) {
        calenderDayViewPagerAdapter.setCurrentDate(currentDate);
    }
}
