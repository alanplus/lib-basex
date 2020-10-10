package com.lib.basex.widget.recycleview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.util.SmartUtil;

/**
 * @author Alan
 * 时 间：2020/10/10
 * 简 述：<功能简述>
 */
public class RecycleViewFactory {

    public static RefreshHeader generatorDefaultRefreshHeader(@NonNull SmartRefreshLayout smartRefreshLayout) {
        ClassicsHeader header = new ClassicsHeader(smartRefreshLayout.getContext()).setSpinnerStyle(SpinnerStyle.FixedBehind);
        header.setSpinnerStyle(SpinnerStyle.Scale);
        setTheme(header, smartRefreshLayout);
        return header;

    }

    public static RefreshFooter generatorDefaultRefreshFooter(Context context) {
        ClassicsFooter footer = new ClassicsFooter(context);
        footer.setBackgroundResource(android.R.color.white);
        footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
        return footer;//指定为经典Footer，默认是 BallPulseFooter
    }

    public static View generatorDefaultCompleteFooter(Context context) {
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        tv.setTextColor(Color.parseColor("#7c7c7c"));
        tv.setLayoutParams(new RecyclerView.LayoutParams(-1, SmartUtil.dp2px(50)));
        tv.setText("已全部加载完成");
        return tv;
    }


    private static void setTheme(@NonNull ClassicsHeader classicsHeader, @NonNull SmartRefreshLayout smartRefreshLayout) {
        Drawable mDrawableProgress = ((ImageView) classicsHeader.findViewById(ClassicsHeader.ID_IMAGE_PROGRESS)).getDrawable();
        if (mDrawableProgress instanceof LayerDrawable) {
            mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
        }
        smartRefreshLayout.getLayout().setBackgroundResource(android.R.color.transparent);
        smartRefreshLayout.setPrimaryColors(0, 0xff666666);
        if (Build.VERSION.SDK_INT >= 21) {
            mDrawableProgress.setTint(0xff666666);
        } else if (mDrawableProgress instanceof VectorDrawableCompat) {
            ((VectorDrawableCompat) mDrawableProgress).setTint(0xff666666);
        }
    }

}
