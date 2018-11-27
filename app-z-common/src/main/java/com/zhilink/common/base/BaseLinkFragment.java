package com.zhilink.common.base;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.umeng.analytics.MobclickAgent;
import com.zhilink.base.BaseFragment;
import com.zhilink.common.R;
import com.zhilink.common.dialogs.FailedDialog;
import com.zhilink.common.dialogs.LoadingDialog;
import com.zhilink.common.dialogs.SuccessDialog;
import com.zhilink.common.dialogs.ZhiLinkDialog;
import com.zhilink.common.net.BaseMethods;
import com.zhilink.common.uitls.ModuleUtils;
import com.zhilink.lib_router.ARouterHelper;

/**
 * 进一步封装fragment
 * 集成友盟统计
 *
 * @author xiemeng
 * @date 2018-8-29 15:55
 */

public abstract class BaseLinkFragment extends BaseFragment {
    /**
     * 读取配置的route名
     */
    protected String propertiesName = "appRoutePath.prop";

    /**
     * 管理rxJava生命周期，防止内存泄漏
     */
    public LifecycleProvider<Lifecycle.Event> lifecycleProvider;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifecycleProvider = AndroidLifecycle.createLifecycleProvider(this);
        BaseMethods.setLifecycleProvider(lifecycleProvider);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity instanceof BaseTitleActivity) {
            BaseTitleActivity titleActivity = (BaseTitleActivity) activity;
            MobclickAgent.onPageStart(TAG + titleActivity.getToolbarTextString());
        } else {
            MobclickAgent.onPageStart(TAG);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (activity instanceof BaseTitleActivity) {
            BaseTitleActivity titleActivity = (BaseTitleActivity) activity;
            MobclickAgent.onPageEnd(TAG + titleActivity.getToolbarTextString());
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }

    public void startActivity(String path) {
        String properties = ModuleUtils.getProperties(activity, propertiesName, path);
        ARouterHelper.startActivity(properties);
    }

    public void startActivityBundle(String path, Bundle bundle) {
        String properties = ModuleUtils.getProperties(activity, propertiesName, path);
        ARouterHelper.startActivityBundle(properties, bundle);
    }

    public void startActivityResult(String path, int requestCode) {
        String properties = ModuleUtils.getProperties(activity, propertiesName, path);
        ARouterHelper.startActivityResult(activity, properties, requestCode);
    }

    public void startActivityBundleResult(String path, Bundle bundle, int requestCode) {
        String properties = ModuleUtils.getProperties(activity, propertiesName, path);
        ARouterHelper.startActivityBundleResult(activity, properties, bundle, requestCode);
    }

    public Fragment getFragment(String path, Bundle bundle) {
        String properties = ModuleUtils.getProperties(activity, propertiesName, path);
        return ARouterHelper.getFragment(properties);
    }
    @Override
    public void showLoadingDialog() {
        LoadingDialog.getInstance().showDialog("", activity);
    }
    @Override
    public void dismissLoadingDialog() {
        LoadingDialog.getInstance().dismissDialog();
    }
    public void showFailedDialog(Object msg, FailedDialog.ClickListener listener) {
        FailedDialog.getInstance().showFailedDialog(msg, activity, listener);
    }

    public void showChooseCommit(ZhiLinkDialog.ClickListener listener) {
        ZhiLinkDialog.getInstance().showDialog(R.string.choose_commit, activity, listener);
    }

    public void showSuccessDialog(Object msg, SuccessDialog.ClickListener listener) {
        SuccessDialog.getInstance().showSuccessDialog(msg, activity, listener);
    }

}
