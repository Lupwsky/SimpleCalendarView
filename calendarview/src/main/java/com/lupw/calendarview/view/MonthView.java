package com.lupw.calendarview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.lupw.calendarview.R;
import com.lupw.calendarview.adapter.RecyclerMonthAdapter;
import com.lupw.calendarview.bean.DateBean;
import com.lupw.calendarview.listener.OnCalenderSelectListener;
import com.lupw.calendarview.listener.OnItemClickListener;
import com.lupw.calendarview.utils.DateTimeUtils;
import org.joda.time.DateTime;

import java.util.List;


/**
 * Created by lupengwei on 2017/11/9.
 * Admin Lupw
 */

public class MonthView extends LinearLayout {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerMonthAdapter recyclerMonthAdapter;
    private OnCalenderSelectListener onCalenderSelectListener;
    private List<DateBean> dataList;

    public MonthView(Context context) {
        super(context);
        init(context);
    }


    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_calendar_month_layout, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rlContent);
        addView(view);
    }


    public void setData(final DateTime date, DateTime startDate, DateTime endDate, DateTime currDate) {
        dataList = DateTimeUtils.getMonthData(date, startDate, endDate, currDate);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 6);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerMonthAdapter = new RecyclerMonthAdapter(context, dataList);
        recyclerMonthAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void listener(int position) {
                onCalenderSelectListener.selected(dataList.get(position).getStrDate());
            }
        });
        recyclerView.setAdapter(recyclerMonthAdapter);
    }


    /**
     * 设置点击监听事件
     *
     * @param onCalenderSelectListener 日历点击监听事件
     */
    public void setOnCalenderSelectListener(OnCalenderSelectListener onCalenderSelectListener) {
        this.onCalenderSelectListener = onCalenderSelectListener;
    }


    public void notifySetDateChange(String strDate) {
        for (DateBean bean : dataList) {
            if (bean.getStrDate().equals(strDate)) {
                bean.setSelected(true);
            } else {
                bean.setSelected(false);
            }
        }
        recyclerMonthAdapter.notifyDataSetChanged();
    }
}
