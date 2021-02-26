package com.lib.basex.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lib.basex.R;
import com.lib.basex.databinding.LTextListDialogBinding;
import com.lib.basex.utils.LUtils;
import com.lib.basex.widget.WithBorderTextView;
import com.lib.basex.widget.recycleview.IBaseRecycleView;
import com.lib.basex.widget.recycleview.LRecycleViewProxy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Arrays;

/**
 * @author Alan
 * 时 间：2021/2/25
 * 简 述：<功能简述>
 */
public class LTextListDialog extends LDialog<LTextListDialogBinding> implements IBaseRecycleView<String> {

    private String[] dataArr;
    private OnItemClickListener onItemClickListener;

    public LTextListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LRecycleViewProxy<String> proxy = new LRecycleViewProxy<>(d.lTextListView);
        proxy.setDataList(Arrays.asList(dataArr));
        proxy.setRecycleView(this);
        proxy.build();

    }

    public void setData(@NonNull String[] arr) {
        this.dataArr = arr;
    }

    @Override
    public int getContentRes() {
        return R.layout.l_text_list_dialog;
    }

    @Override
    public View getView(Context context, int type) {
        WithBorderTextView textView = new WithBorderTextView(getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        textView.setTextColor(Color.parseColor("#535353"));
        textView.setGravity(Gravity.CENTER);
        int padding = LUtils.dip2px(15);
        WithBorderTextView.LineProperty property = new WithBorderTextView.LineProperty();
        property.color = Color.parseColor("#dddddd");
        property.width = LUtils.dip2px(1);
        property.direct = WithBorderTextView.LINE_BOTTOM;
        textView.setLine(property);
        textView.setSingleLine();
        textView.setPadding(padding, padding, padding, padding);
        textView.setBackgroundResource(R.drawable.l_view_press_selector);
        textView.setLayoutParams(new SmartRefreshLayout.LayoutParams(-1, -2));
        return textView;
    }

    @Override
    public void bindView(String s, View view, int position) {
        ((TextView) view).setText(s);
        ((TextView) view).setOnClickListener((View.OnClickListener) v -> {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClickListener(position, s, view);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 每一项的点击
         *
         * @param position 位置
         * @param s        文本
         * @param view     View
         */
        void onItemClickListener(int position, String s, View view);
    }
}
