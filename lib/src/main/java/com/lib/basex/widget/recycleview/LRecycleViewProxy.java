package com.lib.basex.widget.recycleview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2020/10/10
 * 简 述：RecycleView 代理类
 */
public class LRecycleViewProxy<T> {

    @NonNull
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;

    private List<T> mListData;

    private IBaseRecycleView<T> mIBaseRecycleView;
    private RecyclerView.LayoutManager layoutManager;
    //
    private RefreshFooter mRefreshFooter;
    private RefreshHeader mRefreshHeader;
    //
    private OnRefreshListener onRefreshListener;
    private OnLoadMoreListener onLoadMoreListener;
    //
    private BaseRecycleAdapter<T> adapter;
    //
    private View headView;
    private View footView;
    //
    private boolean isReUsed;

    public LRecycleViewProxy(@NonNull SmartRefreshLayout smartRefreshLayout) {
        this.mSmartRefreshLayout = smartRefreshLayout;
        this.mSmartRefreshLayout.setEnableRefresh(false);
        this.mSmartRefreshLayout.setEnableLoadMore(false);
        this.isReUsed = true;
        layoutManager = new LinearLayoutManager(smartRefreshLayout.getContext());
        mRecyclerView = new RecyclerView(smartRefreshLayout.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        mRecyclerView.setLayoutParams(layoutParams);
        this.mSmartRefreshLayout.addView(mRecyclerView);

    }

    public void build() {
        Context context = mSmartRefreshLayout.getContext();
        if (null != onRefreshListener) {
            if (null == mRefreshHeader) {
                mRefreshHeader = RecycleViewFactory.generatorDefaultRefreshHeader(mSmartRefreshLayout);
            }
            mSmartRefreshLayout.setRefreshHeader(mRefreshHeader);
            mSmartRefreshLayout.setOnRefreshListener(onRefreshListener);
            mSmartRefreshLayout.setEnableRefresh(true);
        }

        if (onLoadMoreListener != null) {
            if (null == mRefreshFooter) {
                mRefreshFooter = RecycleViewFactory.generatorDefaultRefreshFooter(context);
            }
            mSmartRefreshLayout.setRefreshFooter(mRefreshFooter);
            mSmartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);//内容不满一页时候启用加载更多
            mSmartRefreshLayout.setOnLoadMoreListener(onLoadMoreListener);
            mSmartRefreshLayout.setEnableAutoLoadMore(false);
            mSmartRefreshLayout.setEnableOverScrollBounce(true);
            mSmartRefreshLayout.setEnableFooterFollowWhenNoMoreData(false);
            mSmartRefreshLayout.setEnableLoadMore(true);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new BaseRecycleAdapter<>(context, mListData, mIBaseRecycleView);

        if (headView != null) {
            adapter.setHeadView(headView);
        }

        if (footView != null) {
            adapter.setFootView(footView);
        }

        mRecyclerView.setAdapter(adapter);
        if (!isReUsed) {
            mRecyclerView.getRecycledViewPool().setMaxRecycledViews(BaseRecycleAdapter.VIEW_TYPE_COMMON, 0);
        }
    }

    public LRecycleViewProxy<T> setDataList(List<T> dataList) {
        this.mListData = dataList;
        return this;
    }

    public LRecycleViewProxy<T> setRecycleView(IBaseRecycleView<T> mIBaseRecycleView) {
        this.mIBaseRecycleView = mIBaseRecycleView;
        return this;
    }

    public LRecycleViewProxy<T> setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    public LRecycleViewProxy<T> setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        return this;
    }

    public LRecycleViewProxy<T> setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    public LRecycleViewProxy<T> setHeadView(View headView) {
        this.headView = headView;
        return this;
    }

    public LRecycleViewProxy<T> setFootView(View footView) {
        this.footView = footView;
        return this;
    }

    @NonNull
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void refresh(List<T> list, boolean isAppend) {
        if (adapter == null) {
            return;
        }

        if (null == mListData) {
            mListData = new ArrayList<>();
        }

        if (isAppend && list != null && list.size() > 0) {
            mListData.addAll(list);
        }

        if (!isAppend) {
            mListData.clear();
            if (null != list && list.size() > 0) {
                mListData.addAll(list);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 当加载完成时，没有更多数据时 调用
     *
     * @param isShowFootView
     * @param footView
     */
    public void complete(boolean isShowFootView, View footView) {
        onLoadMoreListener = null;
        mSmartRefreshLayout.setEnableLoadMore(false);
        if (isShowFootView) {
            if (null == footView) {
                footView = RecycleViewFactory.generatorDefaultCompleteFooter(mSmartRefreshLayout.getContext());
            }
            adapter.setFootView(footView);
        }
        adapter.notifyDataSetChanged();
    }

    public boolean autoRefresh() {
        return mSmartRefreshLayout.autoRefresh();
    }

    public boolean autoRefresh(int delay) {
        return mSmartRefreshLayout.autoRefresh(delay);
    }

    public List<T> getListData() {
        return mListData;
    }
}
