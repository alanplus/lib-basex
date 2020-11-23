package com.lib.basex.activity;

import android.view.View;

import com.lib.basex.R;
import com.lib.basex.databinding.LActivityListBinding;
import com.lib.basex.widget.recycleview.IBaseRecycleView;
import com.lib.basex.widget.recycleview.LRecycleViewProxy;

import java.util.ArrayList;

/**
 * @author Alan
 * 时 间：2020/10/16
 * 简 述：<功能简述>
 */
public abstract class LListActivity<T extends LStateViewModel, H> extends LStateActivity<T, LActivityListBinding> implements IBaseRecycleView<H> {

    protected LRecycleViewProxy<H> lRecycleViewProxy;

    @Override
    public int getContentId() {
        return R.layout.l_activity_list;
    }

    @Override
    public void initView() {
        lRecycleViewProxy = new LRecycleViewProxy<>(d.smartRefreshLayout);
        lRecycleViewProxy.setRecycleView(this);
        initRecycleView(lRecycleViewProxy);
        lRecycleViewProxy.build();
    }

    public void initRecycleView(LRecycleViewProxy<H> lRecycleViewProxy) {
        lRecycleViewProxy.setDataList(new ArrayList<>());
    }

}
