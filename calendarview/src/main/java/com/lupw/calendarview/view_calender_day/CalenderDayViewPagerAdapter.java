package com.lupw.calendarview.view_calender_day;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.lupw.calendarview.bean.DateBean;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import com.lupw.calendarview.listener.OnItemSelectedListener;
import com.lupw.calendarview.utils.DateTimeUtils;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

public class CalenderDayViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<DateTime> dateList;     // 月份的列表，例如2016-01-01至2017-12-12
    private DateTime startDate, endDate; // 开始日期范围和结束日期范围
    private String strCurrDate;          // 当前记录的时间
    private int currPageIndex;           // 当前选择日期的时候ViewPager的位置
    private Map<Integer, DayView> viewMap = new HashMap<>();  // 缓存ViewPager已经加载的View

    private OnCalenderSelectListener onCalenderSelectListener;


    public CalenderDayViewPagerAdapter(Context context, List<DateTime> dateList,
                                       DateTime startDate, DateTime endDate) {
        this.context = context;
        this.dateList = dateList;
        this.startDate = startDate;
        this.endDate = endDate;
        strCurrDate = new DateTime().toString("yyyy-MM-dd");
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
        DayView dayView = new DayView(context, position);
        dayView.setData(DateTimeUtils.getDayData(dateList.get(position), startDate, endDate, new DateTime(strCurrDate)));
        dayView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void listener(DateBean dateBean, int pageIndex) {
                strCurrDate = dateBean.getStrDate();
                currPageIndex = pageIndex;
                Log.e("position", "" + pageIndex);
                if (onCalenderSelectListener != null) onCalenderSelectListener.selected(strCurrDate);
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


    /**
     * 刷新View，viewMap正常会保存三个缓存页面
     *
     */
    public void notifySetDateChange() {
        for (Object o : viewMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            ((DayView) entry.getValue()).setCurretnDate(strCurrDate, currPageIndex);
        }
    }
}
