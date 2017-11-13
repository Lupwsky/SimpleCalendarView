package com.lupw.calendarview.bean;

/**
 * Created by lupengwei on 2017/11/10.
 * Admin Lupw
 */

public class DateBean {
    private String dayIndex;                  // 0, 1, 2, 3, ...
    private String strDate;                // 字符串日期，如果是日2017-09-09，如果数月2017-09
    private boolean isSelected = false;    // 当前日期是否被选中
    private boolean canSelected = true;    // 是否能被点击
    private boolean canSee = true;         // 是否可见

    public DateBean() {
    }

    public String getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(String dayIndex) {
        this.dayIndex = dayIndex;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCanSelected() {
        return canSelected;
    }

    public void setCanSelected(boolean canSelected) {
        this.canSelected = canSelected;
    }

    public boolean isCanSee() {
        return canSee;
    }

    public void setCanSee(boolean canSee) {
        this.canSee = canSee;
    }
}
