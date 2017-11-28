package com.lupw.calendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lupw.calendarview.CalendarView;
import com.lupw.calendarview.R;
import com.lupw.calendarview.listener.OnSelectedListener;
import com.lupw.calendarview.popupwindow.CommonPopupWindow;


/**
 * Created by lupengwei on 2017/11/6.
 * Admin Lupw
 *
 * 日期选择，基于CommonPopupWindow和material-calendarview实现
 */

public class PopupWindowCalendar {
    private Context context;
    private View aimsView;
    private CommonPopupWindow commonPopupWindow;

    CalendarView calendarView;
    private OnDateSelectListener onDateSelectListener;

    public PopupWindowCalendar(Context context, View aimsView) {
        this.context = context;
        this.aimsView = aimsView;

        // 加载日历视图
        View view = LayoutInflater.from(context).inflate(R.layout.popup_window_calendar_view, null);
        commonPopupWindow = new CommonPopupWindow.Builder(context)
                .setView(view)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)
//                .setAnimationStyle(R.style.topPushAnimation)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(CommonPopupWindow popupWindow, View contentView, int layoutResId) {
                    }
                })
                .create();

        // 获取控件
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        // 初始化日历
        calendarView.setOnCalenderSelectListener(new OnSelectedListener() {
            @Override
            public void selected(String date, boolean isDay) {
                if (onDateSelectListener != null) onDateSelectListener.onDateSelect(date, isDay);
                commonPopupWindow.dismiss();
            }
        });
        calendarView.create();
    }


    public void setMonthSelectEnable(boolean enable) {
        calendarView.setMonthSelectEnable(enable);
    }


    public void show() {
        if (!commonPopupWindow.isShowing()) {
            commonPopupWindow.showAsDropDown(aimsView);
        }
    }


    public void dismiss() {
        if (commonPopupWindow.isShowing()) {
            commonPopupWindow.dismiss();
        }
    }


    /**
     * 日期选择接口回调
     *
     */
    public interface OnDateSelectListener{
        void onDateSelect(String strDate, boolean isDay);
    }

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        this.onDateSelectListener = onDateSelectListener;
    }
}
