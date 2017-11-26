package com.lupw.calendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lupengwei on 2017/11/25.
 * Admin Lupw
 */

public class WheelView extends View {
    private GestureDetector gestureDetector;

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
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.restore();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private void init(Context context) {
        setClickable(true);
        gestureDetector = new GestureDetector(context, new FlingOnGestureListener());
    }


    /**
     * SimpleOnGestureListener 支持的动作如下
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
            Log.e("onDown", "onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("onFling", "onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("onLongPress", "onLongPress");
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("onScroll", "onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("onShowPress", "onShowPress");
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("onSingleTapUp", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }
    }
}
