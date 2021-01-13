package com.lib.basex.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.basex.R;
import com.lib.basex.utils.LUtils;


/**
 * @author Alan
 * 时 间：2020/11/19
 * 简 述：进度条指示器 配合进度条使用
 */
public class RectangleIndicator extends View {

    public String text;
    public int textColor;
    public int textSize;
    public int bgColor;
    public int radius;

    public int width;

    public Paint paint;
    private Paint bgPaint;

    public int indicatorHeight;

    public int num;

    private int progressBarWidth;

    private int startP;

    public RectangleIndicator(Context context) {
        super(context);
    }

    public RectangleIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RectangleIndicator);
        num = array.getInt(R.styleable.RectangleIndicator_l_ri_num, 3);
        textColor = array.getColor(R.styleable.RectangleIndicator_l_ri_text_color, Color.BLACK);
        bgColor = array.getColor(R.styleable.RectangleIndicator_l_ri_bg_color, Color.BLUE);
        textSize = array.getDimensionPixelOffset(R.styleable.RectangleIndicator_l_ri_text_size, 15);
        radius = array.getDimensionPixelOffset(R.styleable.RectangleIndicator_l_ri_radius, 3);
        indicatorHeight = array.getDimensionPixelOffset(R.styleable.RectangleIndicator_l_ri_indicator_height, 8);
        array.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        paint.setTextSize(textSize);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL);
        text = "0%";
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = getViewWidth();
        int viewHeight = getViewHeight();
        setMeasuredDimension(viewWidth, viewHeight);
        startP = -viewWidth / 2;
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int textHeight = LUtils.getTextHeight(paint) + getPaddingTop() + getPaddingBottom();
        RectF rectF = new RectF(0, 0, measuredWidth, textHeight);
        canvas.drawRoundRect(rectF, radius, radius, bgPaint);
        Path path = new Path();
        path.moveTo((float) (measuredWidth / 2 - indicatorHeight / 2), textHeight);
        path.lineTo((float) measuredWidth / 2, measuredHeight);
        path.lineTo((float) (measuredWidth / 2 + indicatorHeight / 2), textHeight);
        path.close();
        canvas.drawPath(path, bgPaint);
        // 绘制文字
        float stringWidth = LUtils.getTextWidth(paint, text);
        float x = (getWidth() - stringWidth) / 2;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float y = (float) textHeight / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText(text, x, y, paint);

    }

    private int getViewWidth() {
        if (width == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < num; i++) {
                sb.append("9");
            }
            width = LUtils.getTextWidth(paint, sb.toString());
            width += getPaddingLeft();
            width += getPaddingRight();
        }
        return width;
    }

    private int getViewHeight() {
        int h = LUtils.getTextHeight(paint);
        return h + getPaddingTop() + getPaddingBottom() + indicatorHeight;
    }

    public void setProgressBar(@NonNull View progressBar) {
        progressBar.post(() -> {
            int width = progressBar.getWidth();
            initProgressBarWidth(width);
            setVisibility(View.VISIBLE);
        });
    }

    public void initProgressBarWidth(int width) {
        this.progressBarWidth = width;
//        postInvalidate();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).leftMargin = startP;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) layoutParams).leftMargin = startP;
        } else if (layoutParams instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) layoutParams).leftMargin = startP;
        }
        requestLayout();
    }

    public void setProgress(int progress, int max) {
        float x = (float) progressBarWidth * progress / max;
        int p = progress * 100 / max;
        text = p + "%";
        postInvalidate();
        setTranslationX(x);
    }
}
