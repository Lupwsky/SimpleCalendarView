package com.lupw.calendarview.view_calender_day;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.lupw.calendarview.R;
import com.lupw.calendarview.bean.DateBean;
import com.lupw.calendarview.listener.OnItemSelectedListener;
import com.lupw.calendarview.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lupengwei on 2017/11/23.
 * Admin Lupw
 */

public class DayView extends View {
    private Context context;
    private int pageIndex = 0;  // 用于记录这个View位于ViewPager的哪一个位置，这个值必须设置
    private int viewHeight;     // view的高度，自定义ViewPager测量高度需要
    private int itemWidth, itemHeight, itemRadius;  // item的宽度、高度和半径
    private int itemRowCount = 6;         // 总共有多少行item
    private int itemColumnCount = 7;      // 一行有多少个item
    private int itemMargin = 5;           // item的边距
    private int itemWeekHeight;           // 周item的高度

    private String[] weekArray = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private List<Rect> weekRectList;    // 周的区域列表
    private List<DateBean> dataList;    // item对应的数据
    private List<Rect> rectList;        // item对应的区域
    private Paint paint;                // 画背景
    private Paint textPaint;            // 画文字
    private float downX, downY;         // Touch的Down事件的X，Y坐标

    private OnItemSelectedListener onItemSelectedListener;

    public DayView(Context context, int pageIndex) {
        super(context);
        init(context);
        this.pageIndex = pageIndex;
    }

    public DayView(Context context) {
        super(context);
        init(context);
    }


    public DayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public DayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context) {
        this.context = context;
        setClickable(true);   // view需要设置为true，否则不能捕捉到监听事件
        itemWeekHeight = DensityUtil.dp2px(context, 40);

        weekRectList = new ArrayList<>();
        rectList = new ArrayList<>();
        dataList = new ArrayList<>();

        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.colorGrayLine));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);  // 填充并且描边
        paint.setStrokeWidth(DensityUtil.dp2px(context, 0.5f));
        paint.setFilterBitmap(true);                  // 防止绘制Bitmap时出现锯齿

        textPaint = new Paint();
        textPaint.setColor(context.getResources().getColor(R.color.colorBlackText));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DensityUtil.dp2px(context, 12f));
        textPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取宽-测量规则的模式和大小
        // int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        // int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        int mWidth = widthSize - 1;
        itemWidth = itemHeight = mWidth / itemColumnCount;
        int defaultItemHeight = DensityUtil.dp2px(context, 42);
        if (itemHeight > defaultItemHeight) itemHeight = defaultItemHeight;
        int mHeight = itemHeight * itemRowCount + itemWeekHeight + DensityUtil.dp2px(context, 10);
        viewHeight = mHeight;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT
                && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = itemHeight = w / itemColumnCount;
        int defaultItemHeight = DensityUtil.dp2px(context, 42);
        if (itemHeight > defaultItemHeight) itemHeight = defaultItemHeight;
        itemRadius = itemHeight / 2 - itemMargin;
        setItemRect();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        int baseLineY;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;        // 为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;  // 为基线到字体下边框的距离

        // 周
        Rect tempRect;
        String strWeek;
        paint.setColor(context.getResources().getColor(R.color.colorGrayLine));
        textPaint.setColor(context.getResources().getColor(R.color.colorGrayText));
        canvas.drawLine(0, 1, getWidth(), 1, paint);
        for (int i = 0; i < weekArray.length; i++) {
            tempRect = weekRectList.get(i);
            strWeek = weekArray[i];
            baseLineY = (int) (tempRect.centerY() - top/2 - bottom/2);
            canvas.drawText(strWeek, tempRect.centerX(), baseLineY, textPaint);
        }
        canvas.drawLine(0, itemWeekHeight, getWidth(), itemWeekHeight, paint);

        // 日
        DateBean dateBean;
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            dateBean = dataList.get(i);
            tempRect = rectList.get(i);
            baseLineY = (int) (tempRect.centerY() - top/2 - bottom/2);

            if (dateBean.isSelected()) {
                paint.setColor(context.getResources().getColor(R.color.colorCalenderPrimary));
                canvas.drawCircle(tempRect.centerX(), tempRect.centerY(), itemRadius, paint);
                textPaint.setColor(context.getResources().getColor(R.color.colorWhite));
            } else {
                if (dateBean.isCanSelected())
                    textPaint.setColor(context.getResources().getColor(R.color.colorBlackText));
                else
                    textPaint.setColor(context.getResources().getColor(R.color.colorLightGrayText));

                if (!dateBean.isCanSee())
                    textPaint.setColor(context.getResources().getColor(R.color.colorTransparent));
            }

            canvas.drawText(dateBean.getDayIndex(), tempRect.centerX(), baseLineY, textPaint);
        }
        canvas.restore();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int size = rectList.size();
                DateBean dateBean;
                Rect tempRect;
                for (int i = 0; i < size; i++) {
                    tempRect = rectList.get(i);
                    dateBean = dataList.get(i);
                    if (tempRect.contains((int) x, (int) y) && tempRect.contains((int) downX, (int) downY)) {
                        if (dateBean.isCanSelected()) {
                            setCurretnDate(i);
                            if (onItemSelectedListener != null) onItemSelectedListener.listener(dateBean, pageIndex);
                        }
                        return true;
                    }
                }
                downX = downY = 0;
                return false;
        }
        return super.onTouchEvent(event);
    }


    private void setItemRect() {
        int row, column;
        weekRectList.clear();
        for (int i = 0; i < weekArray.length; i++) {
            column = i % itemColumnCount;
            weekRectList.add(new Rect(column * itemWidth, 0, (column + 1) * itemWidth, itemWeekHeight));
        }

        int marginWeekTop = DensityUtil.dp2px(context, 5f);
        rectList.clear();
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            row = i / itemColumnCount;
            column = i % itemColumnCount;
            rectList.add(new Rect(column * itemWidth, row * itemHeight + itemWeekHeight + marginWeekTop,
                    (column + 1) * itemWidth, (row + 1) * itemHeight + itemWeekHeight + marginWeekTop));
        }
    }


    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }


    public void setData(List<DateBean> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        setItemRect();
        invalidate();
    }


    public void setCurretnDate(String strCurrDate, int pageIndex) {
        for (DateBean bean : dataList) {
            if (bean.getStrDate().equals(strCurrDate)) {
                if (this.pageIndex == pageIndex) bean.setSelected(true);
                else bean.setSelected(false);
            } else {
                bean.setSelected(false);
            }
        }
        invalidate();
    }

    public void setCurretnDate(int position) {
        for (DateBean bean : dataList) {
            bean.setSelected(false);
        }
        dataList.get(position).setSelected(true);
        invalidate();
    }


    public int getViewHeight() {
        return viewHeight;
    }
}


















