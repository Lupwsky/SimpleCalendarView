package com.lupw.calendarview.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import com.lupw.calendarview.adapter.CalenderViewMonthPagerAdapter;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

public class CalendarViewMonthPager extends ViewPager {
    private Context context;
    private CalenderViewMonthPagerAdapter calenderViewMonthPagerAdapter;
    private OnCalenderSelectListener onCalenderSelectListener;

    public CalendarViewMonthPager(Context context) {
        super(context);
        init(context, null);
    }


    public CalendarViewMonthPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        setOverScrollMode(OVER_SCROLL_NEVER);   // 设置为不能滑动时继续滑动不出现阴影效果
        setOffscreenPageLimit(0);
    }


    public void setDate(List<DateTime> dateList, DateTime startDate, DateTime endDate) {
        calenderViewMonthPagerAdapter =
                new CalenderViewMonthPagerAdapter(context, dateList, startDate, endDate);
        calenderViewMonthPagerAdapter.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                onCalenderSelectListener.selected(date);
                calenderViewMonthPagerAdapter.notifySetDateChange();
            }
        });
        setAdapter(calenderViewMonthPagerAdapter);
    }


    public void setOnCalenderSelectListener(OnCalenderSelectListener onCalenderSelectListener) {
        this.onCalenderSelectListener = onCalenderSelectListener;
    }

}
