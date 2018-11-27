package com.zhilink.common.base;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.umeng.analytics.MobclickAgent;
import com.zhilink.base.BaseActivity;
import com.zhilink.common.R;
import com.zhilink.common.dialogs.FailedDialog;
import com.zhilink.common.dialogs.LoadingDialog;
import com.zhilink.common.dialogs.SuccessDialog;
import com.zhilink.common.dialogs.ZhiLinkDialog;
import com.zhilink.common.net.BaseMethods;
import com.zhilink.common.net.LinkObserver;
import com.zhilink.common.uitls.Constant;
import com.zhilink.common.uitls.ModuleUtils;
import com.zhilink.common.uitls.MyActivityManager;
import com.zhilink.common.uitls.UrlPath;
import com.zhilink.common.zxing.MipCaActivityCapture;
import com.zhilink.lib_router.ARouterHelper;
import com.zhilink.permission.PermissionHelper;
import com.zhilink.permission.PermissionInterface;
import com.zhilink.utils.LogUtils;
import com.zhilink.utils.ViewUtils;
import com.zhilink.zxing.camera.GetBarCodeListener;

/**
 * 含toolbar
 * 可能封装基础业务
 * 所有activity必须继承此类
 * 引用了ARouter执行跳转工作
 * 友盟统计
 *
 * @author xiemeng
 * @date 2018-8-27 13:58
 */

public abstract class BaseTitleActivity extends BaseActivity implements PermissionInterface {

    private LinearLayout parentLinearLayout;

    protected ImageView ivBack;

    protected TextView tvToolbarTitle;

    protected TextView tvToolbarCenterTitle;

    protected TextView tvRightText;

    protected ImageView ivMessage;

    protected ImageView ivScan;

    protected ImageView ivSearch;

    protected Toolbar toolbar;
    /**
     * 管理rxJava生命周期，防止内存泄漏
     */
    public LifecycleProvider<Lifecycle.Event> lifecycleProvider;
    /**
     * 摄像头权限申请
     */
    private PermissionHelper mPermissionCameraHelper;

    @Override
    protected void onResume() {
        super.onResume();
        //统计页面
        MyActivityManager.getInstance().setCurrentActivity(activity);
        MobclickAgent.onPageStart(TAG + getToolbarTextString());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG + getToolbarTextString());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void beforeCreateView() {
        lifecycleProvider = AndroidLifecycle.createLifecycleProvider(this);
        BaseMethods.setLifecycleProvider(lifecycleProvider);
        if (isUseToolBar()) {
            initToolbar(R.layout.toolbar);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ModuleUtils.isCanRequest = true;
    }

    /**
     * 加入toolbar布局
     *
     * @param layoutResID 布局文件
     */
    private void initToolbar(@LayoutRes int layoutResID) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setFitsSystemWindows(true);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    /**
     * @param layoutResID the layout id of sub Activity
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (isUseToolBar()) {
            LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
            toolbar = findViewById(R.id.toolbar);
            tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
            ivBack = findViewById(R.id.ivBack);
            ivMessage = findViewById(R.id.iv_message);
            ivScan = findViewById(R.id.iv_scan);
            ivSearch = findViewById(R.id.iv_search);
            tvToolbarCenterTitle = findViewById(R.id.tv_toolbar_center_title);
            tvRightText = findViewById(R.id.tv_right_text);
            ivMessage.setVisibility(isShowMessage() ? View.VISIBLE : View.GONE);
            ivScan.setVisibility(isShowScan() ? View.VISIBLE : View.GONE);
            ivSearch.setVisibility(isShowSearch() ? View.VISIBLE : View.GONE);
            setBackIcon();
            setScan();
        } else {
            super.setContentView(layoutResID);
        }
    }

    protected void titleClick() {

    }

    ;

    @Override
    public void onBackPressed() {
        if (isHasExitDialog()) {
            ZhiLinkDialog.getInstance().showDialog(R.string.choose_exit_this_page, activity, new ZhiLinkDialog.ClickListener() {
                @Override
                public void onSure() {
                    activity.finish();
                }

                @Override
                public void cancel() {

                }
            });
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 返回
     */
    private void setBackIcon() {
        if (null != ivBack && isShowBacking()) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        if (null != tvToolbarTitle) {
            tvToolbarTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * 摄像头扫描二维码
     */
    private void setScan() {
        if (null != ivScan) {
            ivScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPermissionCameraHelper = new PermissionHelper(activity, BaseTitleActivity.this);
                    mPermissionCameraHelper.requestPermissions();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionCameraHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getPermissionsRequestCode() {
        return Constant.PERMISSIONS_CODE;
    }

    @Override
    public String[] getPermissions() {
        return Constant.PERMISSIONS_SCAN;
    }


    @Override
    public void requestPermissionsSuccess() {
        MipCaActivityCapture.startCameraActivity(activity, new GetBarCodeListener() {
            @Override
            public void onSuccess(String msg) {
                View focusView = ViewUtils.getFocusView(activity);
                if (focusView instanceof EditText) {
                    final EditText et = (EditText) focusView;
                    KeyListener listener = et.getKeyListener();
                    if (null != listener) {
                        et.setText(msg);
                        et.setSelection(msg.length());
                        ModuleUtils.setAction(et);
                    }
                }
            }
        });
    }

    @Override
    public void requestPermissionsFail() {
//        showFailedDialog(R.string.cant_get_permission);
        LogUtils.d(TAG, "requestPermissionsFail");
    }

    /**
     * set Title
     *
     * @param title 标题
     */
    public void setToolBarTitle(CharSequence title) {
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setText(title);
            titleClick();
        }
    }

    /**
     * get Title
     */
    public String getToolbarTextString() {
        if (tvToolbarTitle != null) {
            return tvToolbarTitle.getText().toString();
        } else {
            return TAG;
        }
    }

    /**
     * the toolbar of this Activity
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * @return 是否显示返回
     */
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * @return 是否使用父类toolbar，false则重写
     */
    protected boolean isUseToolBar() {
        return true;
    }

    public void startActivity(String path) {
        ARouterHelper.startActivity(path);
    }

    public void startActivityBundle(String path, Bundle bundle) {
        ARouterHelper.startActivityBundle(path, bundle);
    }

    public void startActivityResult(String path, int requestCode) {
        ARouterHelper.startActivityResult(activity, path, requestCode);
    }

    public void startActivityBundleResult(String path, Bundle bundle, int requestCode) {
        ARouterHelper.startActivityBundleResult(activity, path, bundle, requestCode);
    }

    public Fragment getFragment(String path) {
        return ARouterHelper.getFragment(path);
    }

    /**
     * 退出时弹窗
     *
     * @return true 弹窗 false不弹
     */
    public boolean isHasExitDialog() {
        return false;
    }

    /**
     * 是否显示消息
     */
    public boolean isShowMessage() {
        return false;
    }

    /**
     * 是否显示扫码
     */
    public boolean isShowScan() {
        return false;
    }

    /**
     * 是否显示搜索
     */
    public boolean isShowSearch() {
        return false;
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


