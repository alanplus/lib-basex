package com.lib.basex.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.lib.basex.R;
import com.lib.basex.databinding.LListPopupwindowBinding;
import com.lib.basex.utils.LUtils;
import com.lib.basex.widget.LPopupWindow;
import com.lib.basex.widget.LWithBorderTextView;
import com.lib.basex.widget.recycleview.IBaseRecycleView;
import com.lib.basex.widget.recycleview.LRecycleViewProxy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2021/3/2
 * 简 述：<功能简述>
 */
public class LListPopupWindow extends LPopupWindow<LListPopupwindowBinding> implements IBaseRecycleView<String> {
    protected LRecycleViewProxy<String> proxy;
    protected Context context;

    protected OnItemClickListener onItemClickListener;

    public LListPopupWindow(Context context) {
        super(context);
        setWidth(getItemWidth());
        this.context = context;
        proxy = new LRecycleViewProxy<>(binding.lSmartRefreshLayout);
        proxy.setRecycleView(this);
        List<String> list = new ArrayList<>();
        proxy.setDataList(list);
        proxy.build();
    }

    @Override
    protected int getContentId() {
        return R.layout.l_list_popupwindow;
    }

    @Override
    public View getView(Context context, int type) {
        LWithBorderTextView withBorderTextView = new LWithBorderTextView(context);
        LWithBorderTextView.LineProperty property = new LWithBorderTextView.LineProperty();
        property.direct = LWithBorderTextView.LINE_BOTTOM;
        property.width = LUtils.dip2px(1);
        property.color = Color.parseColor("#d9d9d9");
        withBorderTextView.setLine(property);
        withBorderTextView.setTextColor(Color.parseColor("#535353"));
        withBorderTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        withBorderTextView.setGravity(Gravity.CENTER_VERTICAL);
        int i = LUtils.dip2px(15);
        withBorderTextView.setPadding(i, 0, 0, 0);
        SmartRefreshLayout.LayoutParams layoutParams = new SmartRefreshLayout.LayoutParams(getItemWidth(), getItemHeight());
        withBorderTextView.setLayoutParams(layoutParams);
        withBorderTextView.setBackgroundResource(R.drawable.l_view_press_selector);
        initTextView(withBorderTextView);
        return withBorderTextView;
    }

    protected void initTextView(LWithBorderTextView textView) {

    }

    protected int getItemWidth() {
        return LUtils.dip2px(150);
    }

    protected int getItemHeight() {
        return LUtils.dip2px(40);
    }

    @Override
    public void bindView(String s, View view, int position) {
        ((LWithBorderTextView) view).setText(s);
        if (null != onItemClickListener) {
            view.setOnClickListener(v -> {
                dismiss();
                onItemClickListener.onItemClickListener(v, position, s);
            });
        }
    }

    public void setData(List<String> list) {
        proxy.refresh(list, false);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 每一项 的点击事件
         *
         * @param view     点击View
         * @param position 位置
         * @param s        文本
         */
        void onItemClickListener(View view, int position, String s);
    }
}
