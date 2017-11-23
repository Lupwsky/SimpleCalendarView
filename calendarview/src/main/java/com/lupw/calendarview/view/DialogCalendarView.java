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

/**
 * Created by lupengwei on 2017/11/23.
 * Admin Lupw
 */

public class DialogCalendarView extends DialogFragment {
    private CalendarView calendarView;
    private OnSelectListener onSelectListener;

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
        View view = inflater.inflate(R.layout.dialog_calendar_view, null, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnCalenderSelectListener(new OnSelectListener() {
            @Override
            public void selected(String date, boolean isDayMode) {
                if (onSelectListener != null) onSelectListener.selected(date, isDayMode);
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
