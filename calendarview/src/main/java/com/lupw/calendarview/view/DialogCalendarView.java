package com.lupw.calendarview.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.*;
import com.lupw.calendarview.R;
import com.lupw.calendarview.listener.OnSelectListener;
import com.lupw.calendarview.utils.DensityUtil;
import org.joda.time.DateTime;

/**
 * Created by lupengwei on 2017/11/23.
 * Admin Lupw
 */

public class DialogCalendarView extends DialogFragment {
    private OnSelectListener onSelectListener;
    private CalendarView calendarView;
    private DateTime currDate;

    public static DialogCalendarView getInstance() {
        return new DialogCalendarView();
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
        View view = inflater.inflate(R.layout.dialog_calendar_view, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        DateTime dateTime = new DateTime();
        int endYear = dateTime.plusYears(1).getYear();
        int endMonth = dateTime.getMonthOfYear();
        int endDay = dateTime.getDayOfMonth();
        if (calendarView != null) currDate = calendarView.getCurrDate();
        currDate = currDate == null ? new DateTime() : currDate;
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setRangeDate(2016, 10, 6, endYear, endMonth, endDay);
        calendarView.setCurrentDate(currDate.getYear(), currDate.getMonthOfYear(), currDate.getDayOfMonth());
        calendarView.setOnCalenderSelectListener(new OnSelectListener() {
            @Override
            public void selected(String date, boolean isDayMode) {
                if (onSelectListener != null) onSelectListener.selected(date, isDayMode);
                dismiss();
            }
        });
        calendarView.setMonthSelectEnable(false);
        calendarView.create();
        return view;
    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }
}
