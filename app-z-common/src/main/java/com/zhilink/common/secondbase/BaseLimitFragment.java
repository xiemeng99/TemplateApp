package com.zhilink.common.secondbase;

import android.content.Context;

import com.zhilink.common.base.BaseLinkFragment;
import com.zhilink.utils.LogUtils;

/**
 * 分页相关
 * @author xiemeng
 * @date 2018-10-10 16:19
 */

public abstract class BaseLimitFragment extends BaseLinkFragment {
    /**
     * 当前页数
     */
    protected int mCurrentPage;
    /**
     * 总笔数
     */
    protected int mCount;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCurrentPage = 1;
        mCount=0;
    }

    public void firstUpload(){

    }


}
