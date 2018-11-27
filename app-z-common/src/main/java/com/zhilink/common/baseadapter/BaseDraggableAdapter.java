package com.zhilink.common.baseadapter;

import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

import java.util.List;

/**
 * 滑动删除
 * @author xiemeng
 * @date 2018-10-30 18:48
 */
public abstract class BaseDraggableAdapter<T, K extends BaseViewHolder> extends BaseItemDraggableAdapter<T, K> {

    public BaseDraggableAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
        setLoadMoreView(new CustomLoadMoreView());
        disableLoadMoreIfNotFullPage();
    }

    /**
     * 第一次加载不进入分页回调
     */
    @Override
    public void disableLoadMoreIfNotFullPage() {

    }

    /**
     * 加载的view
     *
     * @param loadingView 正在加载
     */
    @Override
    public void setLoadMoreView(LoadMoreView loadingView) {
        super.setLoadMoreView(loadingView);
    }

    @Override
    public void loadMoreEnd(boolean is) {
        super.loadMoreEnd(false);
    }

    /**
     * 没有数据时显示
     */
    @Override
    public void setEmptyView(View emptyView) {
        super.setEmptyView(emptyView);
    }

    /**
     * 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
     */
    @Override
    public void setPreLoadNumber(int preLoadNumber) {
        super.setPreLoadNumber(1);
    }
}
