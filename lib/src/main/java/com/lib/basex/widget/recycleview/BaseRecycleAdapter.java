package com.lib.basex.widget.recycleview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * @author Alan
 * 时 间：2020-05-14
 * 简 述：<功能简述>
 */
public class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleAdapter.CommonViewHolder> {

    private List<T> list;
    private IBaseRecycleView<T> iRecycleView;
    private Context context;


    public static final int VIEW_TYPE_COMMON = 1;
    public static final int VIEW_TYPE_HEAD = 2;
    public static final int VIEW_TYPE_FOOT = 3;
    public static final int VIEW_TYPE_TITLE = 4;

    private View mHeadView;

    private View mFootView;


    public BaseRecycleAdapter(@NonNull Context context, List<T> list, @NonNull IBaseRecycleView<T> iRecycleView) {
        this.iRecycleView = iRecycleView;
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return getHeadSize() + getFootSize() + getContentSize();
    }

    private int getHeadSize() {
        return mHeadView == null ? 0 : 1;
    }

    private int getFootSize() {
        return mFootView == null ? 0 : 1;
    }

    public int getContentSize() {
        return null == list ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && null != mHeadView) {
            return VIEW_TYPE_HEAD;
        }

        if (position == getItemCount() - 1 && null != mFootView) {
            return VIEW_TYPE_FOOT;
        }
        return VIEW_TYPE_COMMON;
    }


    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        Log.d("test_recycle", "type:" + type);
        if (type == VIEW_TYPE_HEAD) {
            return new HeadViewHolder(mHeadView);
        }

        if (type == VIEW_TYPE_FOOT) {
            return new FootViewHolder(mFootView);
        }

        return new CommonViewHolder(iRecycleView.getView(context, VIEW_TYPE_COMMON));
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        View v = holder.itemView;
        int i = position - getHeadSize();
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_HEAD && iRecycleView instanceof IRecycleView) {
            ((IRecycleView) iRecycleView).bindHeadView(v);
        } else if (viewType == VIEW_TYPE_FOOT && iRecycleView instanceof IRecycleView) {
            ((IRecycleView) iRecycleView).bindFootView(v);
        } else if (viewType == VIEW_TYPE_COMMON) {
            T t = list.get(i);
            iRecycleView.bindView(t, v, i);
        }
    }


    public static class CommonViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        public CommonViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

    }

    public static class HeadViewHolder extends CommonViewHolder {


        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class FootViewHolder extends CommonViewHolder {

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setHeadView(View mHeadView) {
        this.mHeadView = mHeadView;
    }

    public void setFootView(View mFootView) {
        this.mFootView = mFootView;
    }

    private GridSpanSizeLookup gridSpanSizeLookup;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridManager = ((GridLayoutManager) manager);
            if (gridSpanSizeLookup == null) {
                gridSpanSizeLookup = new GridSpanSizeLookup(gridManager);
            }
            gridManager.setSpanSizeLookup(gridSpanSizeLookup);
        }
    }

    class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private GridLayoutManager gridLayoutManager;

        public GridSpanSizeLookup(@NonNull GridLayoutManager gridLayoutManager) {
            this.gridLayoutManager = gridLayoutManager;
        }

        @Override
        public int getSpanSize(int position) {
            int itemViewType = getItemViewType(position);
            if (itemViewType == VIEW_TYPE_FOOT) {
                return gridLayoutManager.getSpanCount();  // 如果是FootView直接返回网格布局一行的个数  这里是3
            }
            return 1;  // 正常View的话返回默认1
        }
    }
}
