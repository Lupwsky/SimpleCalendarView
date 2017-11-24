package com.lupw.calendarview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.*;
import com.lupw.calendarview.listener.OnSelectedListener;
import com.lupw.calendarview.utils.DensityUtil;
import org.joda.time.DateTime;

/**
 * Created by lupengwei on 2017/11/23.
 * Admin Lupw
 */

public class DialogCalendarView extends DialogFragment {
    private OnSelectedListener onSelectedListener;
    private CalendarView calendarView;
    private DateTime currDate;

    public static DialogCalendarView getInstance() {
        return new DialogCalendarView();
    }


    /**
     * 获取Dialog实例
     *
     * @param date 只允许2017-06和2017-08-08两种时间格式
     * @return Dialog实例
     */
    public static DialogCalendarView getInstance(String date) {
        DialogCalendarView dialogCalendarView = new DialogCalendarView();
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        dialogCalendarView.setArguments(bundle);
        return dialogCalendarView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.5f;
            windowParams.width = DensityUtil.getWindowWidth(getActivity()) - DensityUtil.dp2px(getActivity(), 60);
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(windowParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calendar_view, null, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置当前选中的时间
        String date = getArguments().getString("date");
        date = date == null ? new DateTime().toString("yyyy-MM-dd") : date;
        if (date.length() < 7) {
            currDate = new DateTime();
        } else if (date.length() == 7) {
            String[] arrDate = date.split("-");
            int year = Integer.parseInt(arrDate[0]);
            int month = Integer.parseInt(arrDate[1]);
            currDate = new DateTime(year, month, 1, 0, 0, 0);
        } else {
            String[] arrDate = date.split("-");
            int year = Integer.parseInt(arrDate[0]);
            int month = Integer.parseInt(arrDate[1]);
            int day = Integer.parseInt(arrDate[2]);
            currDate = new DateTime(year, month, day, 0, 0, 0);
        }

        // 当前选中的日期
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        // 设置最选择的日期的范围
        DateTime dateTime = new DateTime();
        int endYear = dateTime.plusYears(1).getYear();
        int endMonth = dateTime.getMonthOfYear();
        int endDay = dateTime.getDayOfMonth();
        calendarView.setRangeDate(2016, 10, 6, endYear, endMonth, endDay);

        calendarView.setCurrentDate(currDate.getYear(), currDate.getMonthOfYear(), currDate.getDayOfMonth());
        calendarView.setOnCalenderSelectListener(new OnSelectedListener() {
            @Override
            public void selected(String date, boolean isDayMode) {
                if (onSelectedListener != null) onSelectedListener.selected(date, isDayMode);
                dismiss();
            }
        });
        calendarView.setMonthSelectEnable(false);
        calendarView.create();
        return view;
    }


    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }
}
