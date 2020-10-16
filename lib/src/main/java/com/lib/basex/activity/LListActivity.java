package com.lib.basex.activity;

import com.lib.basex.R;
import com.lib.basex.databinding.LActivityListBinding;
import com.lib.basex.widget.recycleview.IBaseRecycleView;
import com.lib.basex.widget.recycleview.LRecycleViewProxy;

/**
 * @author Alan
 * 时 间：2020/10/16
 * 简 述：<功能简述>
 */
public abstract class LListActivity<T extends LViewModel, H> extends LStateActivity<T, LActivityListBinding> implements IBaseRecycleView<H> {

    protected LRecycleViewProxy<H> lRecycleViewProxy;

    @Override
    public int getContentId() {
        return R.layout.l_activity_list;
    }

    @Override
    public void initView() {
        lRecycleViewProxy = new LRecycleViewProxy<>(d.smartRefreshLayout);
        lRecycleViewProxy.setRecycleView(this);
        lRecycleViewProxy.build();
    }

}
