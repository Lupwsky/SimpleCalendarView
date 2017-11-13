package com.lupw.calendarview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import com.lupw.calendarview.view.CalendarView;
import com.lupw.calendarview.view.DayView;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

public class CalenderViewDayPagerAdapter extends PagerAdapter {
    private Context context;
    private List<DateTime> dateList;
    private DateTime startDate, endDate;
    private String strCurrDate;
    private Map<Integer, DayView> viewMap = new HashMap<>();

    private OnCalenderSelectListener onCalenderSelectListener;


    public CalenderViewDayPagerAdapter(Context context, List<DateTime> dateList,
                                       DateTime startDate, DateTime endDate) {
        this.context = context;
        this.dateList = dateList;
        this.startDate = startDate;
        this.endDate = endDate;
        Log.e("TAG", "TEST");
    }


    @Override
    public int getCount() {
        return dateList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 暂时每次新创建一个View，为了提高效率应该设置一个缓存来保存最近的几个View
        DayView dayView = new DayView(context);
        dayView.setData(dateList.get(position), startDate, endDate, CalendarView.currDate);
        dayView.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                strCurrDate = date;
                onCalenderSelectListener.selected(strCurrDate);
            }
        });
        container.addView(dayView);
        viewMap.put(position, dayView);
        return dayView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        viewMap.remove(position);
    }


    public void setOnCalenderSelectListener(OnCalenderSelectListener onCalenderSelectListener) {
        this.onCalenderSelectListener = onCalenderSelectListener;
    }


    public void notifySetDateChange() {
        for (Object o : viewMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            ((DayView) entry.getValue()).notifySetDateChange(strCurrDate);
        }
    }
}
