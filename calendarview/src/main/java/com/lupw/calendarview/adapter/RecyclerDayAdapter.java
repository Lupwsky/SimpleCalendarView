package com.lupw.calendarview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lupw.calendarview.R;
import com.lupw.calendarview.bean.DateBean;
import com.lupw.calendarview.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by lupengwei on 2017/10/17.
 * Admin Lupw
 */

public class RecyclerDayAdapter extends RecyclerView.Adapter<RecyclerDayAdapter.ViewHolder> {
    private Context context;
    private List<DateBean> dataList;
    private OnItemClickListener onItemClickListener;


    public RecyclerDayAdapter(Context context, List<DateBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day_view, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DateBean bean = dataList.get(position);
        holder.txtDay.setTag(holder.getLayoutPosition());

        // 日期是否可以被选择
        if (!bean.isCanSelected()) {
            holder.txtDay.setTextColor(context.getResources().getColor(R.color.colorLightGrayText));
            holder.txtDay.setClickable(false);
        } else {
            holder.txtDay.setTextColor(context.getResources().getColor(R.color.colorBlackText));
            holder.txtDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.listener((Integer) v.getTag());
                    // setSelected((Integer) v.getTag()); 点击的时候在CalendarViewMonthPager里面回调刷新
                }
            });
        }

        // 日期是否可见
        if (!bean.isCanSee()) {
            holder.txtDay.setVisibility(View.GONE);
        } else {
            holder.txtDay.setVisibility(View.VISIBLE);
        }

        // 是否被选择了
        if (bean.isSelected()) {
            holder.txtDay.setSelected(true);
            holder.txtDay.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.txtDay.setSelected(false);
        }

        holder.txtDay.setText(String.valueOf(bean.getDayIndex()));
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;

        ViewHolder(View itemView) {
            super(itemView);
            txtDay = (TextView) itemView.findViewById(R.id.txtDay);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
