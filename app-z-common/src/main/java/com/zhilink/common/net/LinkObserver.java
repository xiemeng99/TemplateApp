package com.zhilink.common.net;

import android.content.Context;
import android.util.Log;

import com.zhilink.common.bean.local.BaseResponseBean;
import com.zhilink.common.uitls.ModuleUtils;
import com.zhilink.retrofit.BaseObserver;
import com.zhilink.retrofit.RxActionManager;
import com.zhilink.utils.LogUtils;
import com.zhilink.utils.StringUtils;

import io.reactivex.disposables.Disposable;

/**
 * 初步解析
 * 注意使用此类返回数据必须对应
 * 如果json返回格式未完全解析或者不符合BaseResponse格式，请不要使用LinkObserver
 *
 * @author xiemeng
 * @date 2018-8-24 17:19
 */

public class LinkObserver<T> extends BaseObserver<BaseResponseBean<T>> {

    /**
     * 含有分页时使用
     */
    public interface ZhiLinkListener<T> {
        /**
         * 成功
         *
         * @param msg   信息
         * @param count 笔数
         * @param var1  数据
         */
        void onNext(String msg, int count, T var1);

        /**
         * 失败
         *
         * @param var1 错误描述
         */
        void onError(String var1);
    }

    private ZhiLinkListener zhiLinkListener;

    private Object reqTag;


    /**
     * 请求
     */
    public LinkObserver(Context context, ZhiLinkListener zhiLinkListener) {
        super(null);
        this.context = context;
        this.zhiLinkListener = zhiLinkListener;
    }

    /**
     * reqTag同一tag不会重复请求
     * 含分页的请求
     */
    public LinkObserver(Object reqTag, Context context, ZhiLinkListener zhiLinkListener) {
        super(null);
        this.reqTag = reqTag;
        this.context = context;
        this.zhiLinkListener = zhiLinkListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        ModuleUtils.isCanRequest = false;
        if (null != reqTag) {
            RxActionManager.get().cancel(reqTag);
            RxActionManager.get().add(reqTag, d);
        }
        showLoadingDialog();
    }


    @Override
    public void onNext(BaseResponseBean baseResponse) {
        if (null == baseResponse) {
            if (null != zhiLinkListener) {
                zhiLinkListener.onError("无数据返回");
            }
        } else {
            String successCode = "1";
            if (successCode.equals(baseResponse.getResultCode())) {
                if (null != zhiLinkListener) {
                    zhiLinkListener.onNext(baseResponse.getResultMsg(), baseResponse.getCount(), baseResponse.getData());
                }
            } else {
                if (null != zhiLinkListener) {
                    zhiLinkListener.onError(baseResponse.getResultMsg());
                }
            }

        }


    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e + "");
        if (null != listener) {
            listener.onError(e.getMessage());
        }
        if (null != listener2) {
            listener2.onError(e.getMessage());
        }
        if (null != zhiLinkListener) {
            zhiLinkListener.onError(StringUtils.isBlank(e.getMessage()) ? String.valueOf(e.toString()) : e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        ModuleUtils.isCanRequest = true;
        dismissLoadingDialog();
        if (null != reqTag) {
            RxActionManager.get().remove(reqTag);
        }
    }


    public void showLoadingDialog() {
        LogUtils.i(TAG, "showLoadingDialog");
    }

    public void dismissLoadingDialog() {
        LogUtils.i(TAG, "dismissLoadingDialog");
    }
}
