package com.lib.basex.utils;

import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author Alan
 * 时 间：2021/1/27
 * 简 述：维词 ClickableSpan 添加点击 效果
 * ClickableSpan onClick 方法中 调用
 * Spannable spannable = ((Spannable) ((TextView) view).getText());
 * Selection.removeSelection(spannable);
 */
public class LLinkMovementMethod extends LinkMovementMethod {

    private static LLinkMovementMethod clickLinkMovementMethod;

    public static LLinkMovementMethod getInstance() {
        if (null == clickLinkMovementMethod) {
            clickLinkMovementMethod = new LLinkMovementMethod();
        }
        return clickLinkMovementMethod;
    }

    private ClickableSpan[] temp;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        int action = event.getAction();
        Logger.d("action init:" + action);
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_CANCEL) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_DOWN) {
                    buffer.setSpan(new BackgroundColorSpan(Color.parseColor("#eeeeee")), buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                    temp = link;
                } else {
                    Logger.d("action:" + action);
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                        temp = null;
                    }
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                return true;
            } else {
                if (action == MotionEvent.ACTION_CANCEL && temp != null && temp.length != 0) {
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), buffer.getSpanStart(temp[0]), buffer.getSpanEnd(temp[0]), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    temp = null;
                }
                Selection.removeSelection(buffer);
            }
        }
        return Touch.onTouchEvent(widget, buffer, event);
    }
}
