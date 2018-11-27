package com.zhilink.common.baseadapter;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.zhilink.common.R;

/**
 * 自定义加载布局
 *
 * @author xiemeng
 * @date 2018-10-29 14:20
 */
public final class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.custom_load_more_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_fail_view;
    }
}
