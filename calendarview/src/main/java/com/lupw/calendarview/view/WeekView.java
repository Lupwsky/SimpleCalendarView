package com.lupw.calendarview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.lupw.calendarview.R;

/**
 * Created by lupengwei on 2017/11/8.
 * Admin Lupw
 */

public class WeekView extends View {
    private String[] weekArray = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private int weekSize = 14;            // 文字尺寸，12px
    private int weekColor = Color.BLACK;  // 文字颜色

    private Paint mPaint;
    private Context context;


    public WeekView(Context context) {
        super(context);
        init(context, null);
    }


    public WeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }



    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        initAttrs(attrs);         // 获取自定义属性的值
        initPaint();              // 初始化画笔
    }


    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WeekView);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.WeekView_weekColor) {
                weekColor = ta.getColor(R.styleable.WeekView_weekColor, weekColor);
            } else if (attr == R.styleable.WeekView_weekSize) {
                weekSize = ta.getDimensionPixelSize(R.styleable.WeekView_weekSize, weekSize);
            }
        }
        ta.recycle();
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(weekColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(weekSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        int width = getWidth();
        int height = getHeight();
        int itemWidth = width / 7;

        for (int i = 0; i < weekArray.length; i++) {
            String text = weekArray[i];
            int textWidth = (int) mPaint.measureText(text);
            int startX = itemWidth * i + (itemWidth - textWidth) / 2;
            int startY = (int) (height / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            canvas.drawText(text, startX, startY, mPaint);
        }
        canvas.restore();
    }
}
