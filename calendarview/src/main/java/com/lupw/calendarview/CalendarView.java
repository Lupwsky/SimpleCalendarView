package com.lupw.calendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import com.lupw.calendarview.listener.OnSelectedListener;
import com.lupw.calendarview.view_calender_day.CalendarDayViewPager;
import com.lupw.calendarview.view_calender_month.CalendarMonthViewPager;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

@SuppressLint("SetTextI18n")
public class CalendarView extends LinearLayout {
    private TextView txtYearMonth;     // 当前年月

    private boolean isDayMode = true;  // 日历模式，true-日期，false-月份
    private String currYear, currMonth; // 当前选中的年份、月份
    private boolean isMonthSelectEnable = true;

    private int startYear, startMonth, startDay;  // 默认为2015-01-01，在init方法中设置
    private int endYear, endMonth, endDay;        // 默认为今天，在init方法中设置

    private List<DateTime> monthList;
    private List<DateTime> yearList;
    private CalendarDayViewPager calendarDayViewPager;
    private CalendarMonthViewPager calendarMonthViewPager;

    private OnSelectedListener onSelectedListener;
    public static DateTime currDate;             // 当前选中的日期

    public CalendarView(Context context) {
        super(context);
        init(context);
    }


    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    /**
     * 初始化控件
     *
     * @param context 上下文
     */
    private void init(Context context) {
        // 默认的时间范围2016-06-10至今日时间
        DateTime today = new DateTime();
        startYear = 2016;
        startMonth = 6;
        startDay = 10;
        endYear = today.getYear();
        endMonth = today.getMonthOfYear();
        endDay = today.getDayOfMonth();
        currDate = currDate == null ? today : currDate;
        currYear = today.toString("yyyy年");
        currMonth = today.toString("yyyy年MM月");

        // 获取视图控件
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_view_layout, null);
        LinearLayout llPre = (LinearLayout) view.findViewById(R.id.llPre);
        LinearLayout llNext = (LinearLayout) view.findViewById(R.id.llNext);
        LinearLayout llYearMoth = (LinearLayout) view.findViewById(R.id.llYearMoth);
        txtYearMonth = (TextView) view.findViewById(R.id.txtYearMonth);
        calendarDayViewPager = (CalendarDayViewPager) view.findViewById(R.id.calendarDayViewPager);
        calendarMonthViewPager = (CalendarMonthViewPager) view.findViewById(R.id.calendarMonthViewPager);

        // 设置top显示的年月，默认为本月
        txtYearMonth.setText(new DateTime().toString("yyyy年MM月"));
        initCalendarDayViewPager();
        initCalendarMonthViewPager();

        // 上一个月
        llPre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem;
                if (isDayMode) {
                    currentItem = calendarDayViewPager.getCurrentItem();
                    if (currentItem > 0) {
                        calendarDayViewPager.setCurrentItem(currentItem - 1);
                    }
                } else {
                    currentItem = calendarMonthViewPager.getCurrentItem();
                    if (currentItem > 0) {
                        calendarMonthViewPager.setCurrentItem(currentItem - 1);
                    }
                }
            }
        });

        // 下一个月
        llNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem;
                if (isDayMode) {
                    currentItem = calendarDayViewPager.getCurrentItem();
                    if (currentItem < monthList.size()) {
                        calendarDayViewPager.setCurrentItem(currentItem + 1);
                    }
                } else {
                    currentItem = calendarMonthViewPager.getCurrentItem();
                    if (currentItem < yearList.size()) {
                        calendarMonthViewPager.setCurrentItem(currentItem - 1);
                    }
                }
            }
        });

        // 年月切换
        llYearMoth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMonthSelectEnable) return;
                if (calendarDayViewPager.getVisibility() == GONE) {
                    calendarDayViewPager.setVisibility(VISIBLE);
                    calendarMonthViewPager.setVisibility(GONE);
                    isDayMode = true;
                    txtYearMonth.setText(currMonth);
                } else {
                    calendarDayViewPager.setVisibility(GONE);
                    calendarMonthViewPager.setVisibility(VISIBLE);
                    isDayMode = false;
                    txtYearMonth.setText(currYear);
                }
            }
        });

        addView(view);
    }


    /**
     * 创建日历视图
     *
     */
    public void create() {
        DateTime startDate = new DateTime(startYear, startMonth, startDay, 0, 0, 0);
        DateTime endDate = new DateTime(endYear, endMonth, endDay, 0, 0, 0);

        monthList = getMonthList();
        calendarDayViewPager.setDate(monthList, startDate, endDate);
        calendarDayViewPager.setCurrentItem(getCurrentMonthPosition(currDate));

        yearList = getYearList();
        calendarMonthViewPager.setDate(yearList, startDate, endDate);
        calendarMonthViewPager.setCurrentItem(getCurrentYearPosition(currDate));
    }


    /**
     * 设置日历的开始时间和结束时间
     *
     * @param startYear   开始年份
     * @param startMonth  开始月份
     * @param startDay    开始天数
     * @param endYear     结束月份
     * @param endMonth    结束月份
     * @param endDay      结束天数
     */
    public void setRangeDate(int startYear, int startMonth, int startDay,
                             int endYear, int endMonth, int endDay) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
    }


    /**
     * 根据开始日期和结束日期获取月份列表
     *
     * @return 月份列表
     */
    public List<DateTime> getMonthList () {
        List<DateTime> monthList = new ArrayList<>();
        DateTime dateTime = new DateTime(startYear, startMonth, 1, 0, 0, 0);
        DateTime tempDateTime;
        int monthCount = (endYear - startYear) * 12 + endMonth - startMonth + 1;
        for (int i = 0; i < monthCount; i++) {
            tempDateTime = dateTime.plusMonths(i);
            monthList.add(tempDateTime);
        }
        return monthList;
    }


    /**
     * 获取
     *
     * @return 当前月份在列表中的位置
     */
    private int getCurrentMonthPosition(DateTime dateTime) {
        int year = dateTime.getYear(), month = dateTime.getMonthOfYear();

        DateTime tempDate;
        for (int i = 0; i < monthList.size(); i++) {
            tempDate = monthList.get(i);
            if (tempDate.getYear() == year && tempDate.getMonthOfYear() == month) {
                return i;
            }
        }
        return 0;
    }



    /**
     * 获取年份列表
     *
     * @return 年份列表
     */
    public List<DateTime> getYearList () {
        List<DateTime> yearList = new ArrayList<>();
        DateTime dateTime = new DateTime(startYear, startMonth, 1, 0, 0, 0);
        DateTime tempDateTime;
        int yearCount = endYear - startYear + 1;
        for (int i = 0; i < yearCount; i++) {
            tempDateTime = dateTime.plusYears(i);
            yearList.add(tempDateTime);
        }
        return yearList;
    }


    /**
     * 获取
     *
     * @return 当前年份在列表中的位置
     */
    private int getCurrentYearPosition(DateTime dateTime) {
        int year = dateTime.getYear();

        DateTime tempDate;
        for (int i = 0; i < yearList.size(); i++) {
            tempDate = yearList.get(i);
            if (tempDate.getYear() == year) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 设置日历选中监听事件
     *
     * @param onSelectedListener 日历选中监听事件
     */
    public void setOnCalenderSelectListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }


    private void initCalendarDayViewPager() {
        // 设置日历控件翻页监听事件
        calendarDayViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (isDayMode) {
                    currMonth = monthList.get(position).toString("yyyy年MM月");
                    txtYearMonth.setText(currMonth);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // 设置选中日历某一天监听事件
        calendarDayViewPager.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                if (onSelectedListener != null) {
                    currDate = new DateTime(date);
                    onSelectedListener.selected(date, isDayMode);
                }
            }
        });
    }


    private void initCalendarMonthViewPager() {
        calendarMonthViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!isDayMode) {
                    currYear = yearList.get(position).toString("yyyy年");
                    txtYearMonth.setText(currYear);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // 设置选中月历监听事件
        calendarMonthViewPager.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                if (onSelectedListener != null) {
                    currDate = new DateTime(date);
                    onSelectedListener.selected(date, isDayMode);
                }
            }
        });
    }


    /**
     * 设置是否可以选择月份
     *
     * @param enable enable
     */
    public void setMonthSelectEnable(boolean enable) {
        isMonthSelectEnable = enable;
    }


    /**
     * 设置当前选中的日期或者月份
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public void setCurrentDate(int year, int month, int day) {
        currDate = new DateTime(year, month, day, 0, 0, 0);
        if (isDayMode) {
            calendarDayViewPager.setCurrentDate(currDate.toString("yyyy-MM-dd"));
            calendarDayViewPager.setCurrentItem(getCurrentMonthPosition(currDate));
        } else {
            calendarMonthViewPager.setCurrentDate(currDate.toString("yyyy-MM"));
            calendarMonthViewPager.setCurrentItem(getCurrentYearPosition(currDate));
        }
    }


    public String getCurrDate() {
        return currDate.toString("yyyy-MM-dd");
    }
}
