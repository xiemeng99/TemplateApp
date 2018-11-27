package com.zhilink.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.zhilink.common.R;
import com.zhilink.utils.ViewUtils;


/**
 * 自定义圆角矩形进度条
 *
 * @author 11
 */
public class DownloadProgressBar extends View {

    /**
     * 进度条最大值
     */
    private float maxCount;
    /**
     * 进度条当前值
     */
    private float currentCount;
    /**
     * 画笔
     */
    private Paint mPaint;

    private int mWidth, mHeight;

    public DownloadProgressBar(Context context) {
        super(context);
    }

    public DownloadProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DownloadProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /***
     * 设置最大的进度值
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * 得到最大进度值
     */
    public double getMaxCount() {
        return maxCount;
    }

    /**
     * 设置当前的进度值
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        //刷新View
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        //设置抗锯齿效果
        mPaint.setAntiAlias(true);
        //设置画笔颜色
        mPaint.setColor(ContextCompat.getColor(getContext(),R.color.main_color));
        int round = mHeight / 4;
        /**
         * RectF：绘制矩形，四个参数分别是left,top,right,bottom
         * 类型是单精度浮点数
         */
        RectF rf = new RectF(0, 0, mWidth, mHeight);
        /*绘制圆角矩形，背景色为画笔颜色*/
        canvas.drawRoundRect(rf, round, round, mPaint);
        mPaint.setColor(ContextCompat.getColor(getContext(),R.color.white));
        RectF rectBlackBg = new RectF(1, 1, mWidth - 1, mHeight - 1);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);
        //设置进度条进度及颜色
        float section = currentCount / maxCount;
        RectF rectProgressBg = new RectF(1, 1, (mWidth - 1) * section, mHeight - 1);
        if (section != 0.0f) {
            mPaint.setColor(ContextCompat.getColor(getContext(),R.color.main_color));
        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = ViewUtils.dp2px(getContext(), 36);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }


}
