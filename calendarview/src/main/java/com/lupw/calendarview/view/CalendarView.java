package com.lupw.calendarview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lupw.calendarview.R;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import com.lupw.calendarview.listener.OnSelectListener;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

@SuppressLint("SetTextI18n")
public class CalendarView extends LinearLayout {
    private Context context;
    private LinearLayout llPre;        // 上一页
    private LinearLayout llNext;       // 下一页
    private LinearLayout llYearMoth;   // 日期选择
    private TextView txtYearMonth;     // 当前年月

    private boolean isDayMode = true;  // 日历模式，true-日期，false-月份
    private String currYear, currMonth; // 当前选中的年份、月份

    private int startYear, startMonth, startDay;  // 默认为2015-01-01，在init方法中设置
    private int endYear, endMonth, endDay;        // 默认为今天，在init方法中设置

    private List<DateTime> monthList;
    private List<DateTime> yearList;
    private CalendarViewDayPager calendarViewDayPager;
    private CalendarViewMonthPager calendarViewMonthPager;

    private OnSelectListener onSelectListener;
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
        this.context = context;

        // 默认的时间范围
        DateTime today = new DateTime();
        startYear = 2016;
        startMonth = 6;
        startDay = 10;
        endYear = today.getYear();
        endMonth = today.getMonthOfYear();
        endDay = today.getDayOfMonth();
        currDate = today;
        currYear = today.toString("yyyy年");
        currMonth = today.toString("yyyy年MM月");

        // 获取视图控件
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_view_layout, null);
        llPre = (LinearLayout) view.findViewById(R.id.llPre);
        llNext = (LinearLayout) view.findViewById(R.id.llNext);
        llYearMoth = (LinearLayout) view.findViewById(R.id.llYearMoth);
        txtYearMonth = (TextView) view.findViewById(R.id.txtYearMonth);
        calendarViewDayPager = (CalendarViewDayPager) view.findViewById(R.id.calendarViewPager);
        calendarViewMonthPager = (CalendarViewMonthPager) view.findViewById(R.id.calendarViewMonthPager);

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
                    currentItem = calendarViewDayPager.getCurrentItem();
                    if (currentItem > 0) {
                        calendarViewDayPager.setCurrentItem(currentItem - 1);
                    }
                } else {
                    currentItem = calendarViewMonthPager.getCurrentItem();
                    if (currentItem > 0) {
                        calendarViewMonthPager.setCurrentItem(currentItem - 1);
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
                    currentItem = calendarViewDayPager.getCurrentItem();
                    if (currentItem < monthList.size()) {
                        calendarViewDayPager.setCurrentItem(currentItem + 1);
                    }
                } else {
                    currentItem = calendarViewMonthPager.getCurrentItem();
                    if (currentItem < monthList.size()) {
                        calendarViewMonthPager.setCurrentItem(currentItem + 1);
                    }
                }
            }
        });

        // 年月切换
        llYearMoth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarViewDayPager.getVisibility() == GONE) {
                    calendarViewDayPager.setVisibility(VISIBLE);
                    calendarViewMonthPager.setVisibility(GONE);
                    isDayMode = true;
                    txtYearMonth.setText(currMonth);
                } else {
                    calendarViewDayPager.setVisibility(GONE);
                    calendarViewMonthPager.setVisibility(VISIBLE);
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
        calendarViewDayPager.setDate(monthList, startDate, endDate);
        calendarViewDayPager.setCurrentItem(monthList.size() - 1);   // 默认滚动到最后一页

        yearList = getYearList();
        calendarViewMonthPager.setDate(yearList, startDate, endDate);
        calendarViewMonthPager.setCurrentItem(yearList.size() - 1);   // 默认滚动到最后一页
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
     * 设置日历选中监听事件
     *
     * @param onSelectListener 日历选中监听事件
     */
    public void setOnCalenderSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }


    private void initCalendarDayViewPager() {
        // 设置日历控件翻页监听事件
        calendarViewDayPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        calendarViewDayPager.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                if (onSelectListener != null) {
                    currDate = new DateTime(date);
                    onSelectListener.selected(date, isDayMode);
                }
            }
        });
    }


    private void initCalendarMonthViewPager() {
        calendarViewMonthPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        // 设置选中日历某一天监听事件
        calendarViewMonthPager.setOnCalenderSelectListener(new OnCalenderSelectListener() {
            @Override
            public void selected(String date) {
                if (onSelectListener != null) {
                    currDate = new DateTime(date);
                    onSelectListener.selected(date, isDayMode);
                }
            }
        });
    }
}
