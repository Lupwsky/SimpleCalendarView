package com.lupw.calendarview.view_wheel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import com.lupw.calendarview.R;
import com.lupw.calendarview.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lupengwei on 2017/11/25.
 * Admin Lupw
 */

public class WheelView extends View {
    private Context context;
    private int viewWidth, viewHeight;
    private List<String> dataList;
    private int itemHeight;             // item高度
    private int visibleItemCount;       // 需要展示的item的数量
    private int currItemPosition;       // 当前展示的item的位置
    private int itemStartPosition;      // 绘制item时开始的位置
    private int itemEndPosition;        // 绘制item时结束的位置
    private int itemSelectedPosition;   // 绘制item的选中时所在的位置，默认为中间的位置
    private float lastMoveX, lastMoveY; // 最后一次滚动时X，Y的坐标
    private float moveLength;           // 移动的距离
    private boolean isScroll;           // 是否正在滚动

    private TextPaint textPaint;
    private GestureDetector gestureDetector;
    private Scroller scroller;


    public WheelView(Context context) {
        super(context);
        init(context);
    }


    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
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
        int defaultItemHeight = DensityUtil.dp2px(context, 42);
        if (itemHeight > defaultItemHeight) itemHeight = defaultItemHeight;
        int mHeight = itemHeight * visibleItemCount;
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
        viewWidth = w;
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制item
        int dataSize = dataList.size();
        for (int i = 0; i < dataSize; i++) {
            StaticLayout staticLayout = new StaticLayout(dataList.get(i), textPaint, viewWidth,
                    Layout.Alignment.ALIGN_CENTER, 1.0f, 1.0f, false);
            canvas.save();
            canvas.translate(0, i * itemHeight + itemHeight / 2 -
                    DensityUtil.dp2px(context, 6f) - (int) moveLength);
            staticLayout.draw(canvas);
            canvas.restore();
        }

        // item选中位置上下两根线条
        canvas.drawLine(0, itemHeight * itemSelectedPosition,
                viewWidth, itemHeight * itemSelectedPosition, textPaint);
        canvas.drawLine(0, itemHeight * (itemSelectedPosition + 1),
                viewWidth, itemHeight * (itemSelectedPosition + 1), textPaint);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private void init(Context context) {
        this.context = context;
        setClickable(true);
        gestureDetector = new GestureDetector(context, new FlingOnGestureListener());
        itemHeight = DensityUtil.dp2px(context, 40f);
        visibleItemCount = 9;
        currItemPosition = 5;
        itemSelectedPosition = 2;
        itemStartPosition = currItemPosition - itemSelectedPosition;
        itemEndPosition = itemStartPosition + visibleItemCount;

        textPaint = new TextPaint();
        textPaint.setColor(context.getResources().getColor(R.color.colorWhite));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DensityUtil.dp2px(context, 12f));

        scroller = new Scroller(context);

        dataList = new ArrayList<>();
        dataList.add("2017-11-01");
        dataList.add("2017-11-02");
        dataList.add("2017-11-03");
        dataList.add("2017-11-04");
        dataList.add("2017-11-05");
        dataList.add("2017-11-06");
        dataList.add("2017-11-07");
        dataList.add("2017-11-08");
        dataList.add("2017-11-09");
        dataList.add("2017-11-10");
        dataList.add("2017-11-11");
        dataList.add("2017-11-12");
        dataList.add("2017-11-13");
        dataList.add("2017-11-14");
        dataList.add("2017-11-15");
        dataList.add("2017-11-16");
        dataList.add("2017-11-17");
        dataList.add("2017-11-18");
        dataList.add("2017-11-19");
        dataList.add("2017-11-20");
        dataList.add("2017-11-21");
        dataList.add("2017-11-22");
        dataList.add("2017-11-23");
        dataList.add("2017-11-24");
        dataList.add("2017-11-25");
        dataList.add("2017-11-26");
        dataList.add("2017-11-27");
        dataList.add("2017-11-28");
        dataList.add("2017-11-29");
        dataList.add("2017-11-30");
    }


    /**
     * SimpleOnGestureListener 支持的动作如下
     *
     * 按下（onDown）       : 刚刚手指接触到触摸屏的那一刹那。
     * 抛掷（onFling）      : 手指在触摸屏上迅速移动，并松开的动作。
     * 长按（onLongPress）  : 手指按在持续一段时间，并且没有松开。
     * 滚动（onScroll）     : 手指在触摸屏上滑动。
     * 按住（onShowPress）  : 手指按在触摸屏上，它有效的时间范围是在按下之后，长按之前。
     * 抬起（onSingleTapUp）: 手指离开触摸屏的那一刹那。
     *
     */
    private class FlingOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            invalidate();
            return true;
        }
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            Log.e("scroller", "X : " + scroller.getCurrX() + " Y : " + scroller.getCurrY());
            invalidate();
        } else {
            Log.e("scroller", "scroll finish");
        }
    }


    public void scrollTo(int position, boolean isMoveUp) {
        if (isMoveUp) {
            scroller.startScroll(0, itemHeight * position, 0, itemHeight * (position + 1));
        } else {
            scroller.startScroll(0, itemHeight * position, 0, itemHeight * (position - 1));
        }
//        scroller.setFinalX();
        invalidate();
    }
}
