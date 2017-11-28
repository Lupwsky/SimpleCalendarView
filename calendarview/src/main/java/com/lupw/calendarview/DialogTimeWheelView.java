package com.lupw.calendarview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.widget.TextView;
import com.lupw.calendarview.listener.OnTimePickerListener;
import com.lupw.calendarview.utils.DensityUtil;

/**
 * Created by lupengwei on 2017/11/23.
 * Admin Lupw
 */

public class DialogTimeWheelView extends DialogFragment {
    private OnTimePickerListener onTimePickerListener;
    private String currHour, currMin, currSec;
    private TimeWheelView.Mode currMode;
    private boolean dialogHaveLoad;

    public static DialogTimeWheelView getInstance(String hour, String min, String sec,
                                                  TimeWheelView.Mode mode) {
        DialogTimeWheelView dialogCalendarView = new DialogTimeWheelView();
        Bundle bundle = new Bundle();
        bundle.putString("hour", hour);
        bundle.putString("min", min);
        bundle.putString("sec", sec);
        bundle.putSerializable("mode", mode);
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
            windowParams.width = DensityUtil.getWindowWidth(getActivity()) - DensityUtil.dp2px(getActivity(), 80);
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
        View view = inflater.inflate(R.layout.dialog_time_wheel_view, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (!dialogHaveLoad) {
            Bundle bundle = getArguments();
            currHour = bundle.getString("hour");
            currMin = bundle.getString("min");
            currSec = bundle.getString("sec");
            currMode = (TimeWheelView.Mode) bundle.getSerializable("mode");
            dialogHaveLoad = true;
        }

        final TimeWheelView timeWheelView = (TimeWheelView) view.findViewById(R.id.timeWheelView);
        timeWheelView.setTime(currHour, currMin, currSec);
        timeWheelView.setMode(currMode);

        TextView txtCancel = (TextView) view.findViewById(R.id.txtCancel);
        TextView txtSubmit = (TextView) view.findViewById(R.id.txtSubmit);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currHour = timeWheelView.getHour();
                currMin = timeWheelView.getMin();
                currSec = timeWheelView.getSec();
                if (onTimePickerListener != null) {
                    onTimePickerListener.selected(currHour, currMin, currSec);
                }
                dismiss();
            }
        });
        return view;
    }


    public void setOnTimePickerListener(OnTimePickerListener onTimePickerListener) {
        this.onTimePickerListener = onTimePickerListener;
    }
}
