package com.lupw.calendarview.utils;

import com.lupw.calendarview.bean.DateBean;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 *
 * 时间的处理，基于joda-date实现
 */

public class DateTimeUtils {

    /**
     * 天数
     *
     * @param dateTime 日期对象
     * @return         该月在视图上的天数
     */
    public static List<DateBean> getDayData(DateTime dateTime, DateTime startDate,
                                                 DateTime endDate, DateTime currDate) {
        List<DateBean> resultData = new ArrayList<>();
        DateBean dateBean;

        int offset = dateTime.getDayOfWeek();
        if (offset == DateTimeConstants.SUNDAY) {
            offset = 0;
        }

        // 补齐上一个月的天数，默认为不可选中
        DateTime lastMonth = dateTime.minusMonths(1);
        int lastMonthMaxDays = lastMonth .dayOfMonth().getMaximumValue();
        for (int i = 0; i < offset; i++) {
            dateBean = new DateBean();
            dateBean.setDayIndex(String.valueOf((lastMonthMaxDays - offset + i + 1)));
            dateBean.setCanSelected(false);
            dateBean.setCanSee(false);
            dateBean.setStrDate(dateTime.minusDays(offset - i).toString("yyyy-MM-dd"));
            resultData.add(dateBean);
        }

        // 这个月的天数
        int maxDay = dateTime.dayOfMonth().getMaximumValue();
        for (int i = 0; i < maxDay; i ++) {
            dateBean = new DateBean();
            dateBean.setDayIndex(String.valueOf(i + 1));

            DateTime tempDate = dateTime.plusDays(i);
            dateBean.setStrDate(tempDate.toString("yyyy-MM-dd"));

            // 判断当前日期是否和开始日期的年月是否相同，相同需要和开始日期和结束日期比较
            // 以此设定不在分为日期为不可选的状态
            if (tempDate.getYear() == startDate.getYear() &&
                    tempDate.getMonthOfYear() == startDate.getMonthOfYear()) {
                if (tempDate.isBefore(startDate)) {
                    dateBean.setCanSelected(false);
                }
            }

            if (tempDate.getYear() == endDate.getYear() &&
                    tempDate.getMonthOfYear() == endDate.getMonthOfYear()) {
                if (tempDate.isAfter(endDate)) {
                    dateBean.setCanSelected(false);
                }
            }

            // 和当前日期比较是不是同一天，如果是同一天，设置为选中状态
            if (tempDate.toString("yyyy-MM-dd").equals(currDate.toString("yyyy-MM-dd"))) {
                dateBean.setSelected(true);
            }

            resultData.add(dateBean);
        }

        // 补齐下一个月的天数，默认为不可选中
        int surplusDays = 42 - maxDay - offset - 1;
        for (int i = 0; i <= surplusDays; i ++) {
            dateBean = new DateBean();
            dateBean.setDayIndex(String.valueOf(i + 1));
            dateBean.setCanSelected(false);
            dateBean.setCanSee(false);
            dateBean.setStrDate(dateTime.plusMonths(1).plusDays(i).toString("yyyy-MM-dd"));
            resultData.add(dateBean);
        }
        return resultData;
    }


    /**
     * 月份
     *
     * @param dateTime  指定的年
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param currDate  当前时间
     * @return          月份
     */
    public static List<DateBean> getMonthData (DateTime dateTime, DateTime startDate,
                                               DateTime endDate, DateTime currDate) {
        List<DateBean> resultData = new ArrayList<>();
        DateBean dateBean;
        DateTime mdateTime, tempDate;
        int currYear = dateTime.getYear();
        mdateTime = new DateTime(dateTime.getYear(), 1, 1, 0, 0, 0);

        for (int i = 0; i < 12; i++) {
            tempDate = mdateTime.plusMonths(i);

            dateBean = new DateBean();
            dateBean.setDayIndex(String.valueOf(i + 1) + "月");
            dateBean.setStrDate(tempDate.toString("yyyy-MM"));

            int limitStartMonth = startDate.getMonthOfYear();
            if (currYear == startDate.getYear()) {
                if (i < limitStartMonth - 1) {
                    dateBean.setCanSee(true);
                    dateBean.setCanSelected(false);
                }
            }

            int limitEndMonth = endDate.getMonthOfYear();
            if (currYear == endDate.getYear()) {
                if (i > limitEndMonth - 1) {
                    dateBean.setCanSee(true);
                    dateBean.setCanSelected(false);
                }
            }

            int currMonth = currDate.getMonthOfYear();
            if (currYear == currDate.getYear()
                    && (currMonth == tempDate.getMonthOfYear())) {
                dateBean.setSelected(true);
            }
            resultData.add(dateBean);
        }
        return resultData;
    }
}
