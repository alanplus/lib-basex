package com.lib.basex.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author Alan
 * 时 间：2021/2/25
 * 简 述：<功能简述>
 */
public class WithBorderTextView extends androidx.appcompat.widget.AppCompatTextView {

    public static final int LINE_LEFT = 8;
    public static final int LINE_TOP = 4;
    public static final int LINE_RIGHT = 2;
    public static final int LINE_BOTTOM = 1;

    private LineProperty line;
    private Paint paint;

    public WithBorderTextView(Context context) {
        this(context, null);
    }

    public WithBorderTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != line) {
            int direct = line.direct;
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
            if ((direct & LINE_BOTTOM) == LINE_BOTTOM) {
                canvas.drawLine(0, height, width, height, paint);
            }
            if ((direct & LINE_RIGHT) == LINE_RIGHT) {
                canvas.drawLine(width, 0, width, height, paint);
            }

            if ((direct & LINE_TOP) == LINE_TOP) {
                canvas.drawLine(0, 0, width, 0, paint);
            }

            if ((direct & LINE_LEFT) == LINE_LEFT) {
                canvas.drawLine(0, 0, 0, height, paint);
            }
        }

    }

    public void setLine(LineProperty line) {
        this.line = line;
        paint.setColor(line.color);
        paint.setStrokeWidth(line.width);
    }

    public static class LineProperty {
        public int width;
        public int color;
        // 0
        public int direct;
    }
}
