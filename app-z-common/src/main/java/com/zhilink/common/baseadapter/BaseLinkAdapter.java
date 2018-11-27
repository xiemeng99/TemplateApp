package com.zhilink.common.baseadapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.zhilink.common.R;

import java.util.List;

/**
 * 可能会封装一些方法，添加动画，分页，预加载等逻辑
 *
 * @author xiemeng
 * @date 2018-10-10 10:58
 */
public abstract class BaseLinkAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public BaseLinkAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        setLoadMoreView(new CustomLoadMoreView());
        disableLoadMoreIfNotFullPage();
        openLoadAnimation(SLIDEIN_LEFT);
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

    public interface RefreshListener {
        /**
         * 重新请求
         */
        void refresh();
    }

    /**
     * 没有数据时显示
     */
    public void setEmptyView(final RefreshListener listener) {
//        LayoutInflater inflater = (LayoutInflater) CurrentActivityUtils.getInstance().getCurrentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.rv_empty_layout, null);
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT));
//        View tvRefresh = view.findViewById(R.id.tv_refresh);
//        tvRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (null != listener) {
//                    listener.refresh();
//                }
//            }
//        });
//        super.setEmptyView(view);
    }

    /**
     * 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
     */
    @Override
    public void setPreLoadNumber(int preLoadNumber) {
        super.setPreLoadNumber(1);
    }

    @Override
    public void openLoadAnimation(int animationType) {
        super.openLoadAnimation(animationType);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < mData.size()) {
            anim.setStartDelay(index * 150);
        }
    }

}
