package com.zhilink.common.dialogs;

import android.app.Activity;
import android.view.ViewGroup;

import com.zhilink.common.R;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.threeballloading.ThreeBallRotationProgressBar;
import com.zhilink.utils.ViewUtils;

/**
 * 加载框
 *
 * @author xiemeng
 * @date 2018-10-24
 */

public class LoadingDialog {
    private boolean mCancelTouchOut;

    private boolean mBackCancelTouchOut;


    private LoadingDialog() {
    }

    public static LoadingDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private static class MainHolder {
        private static final LoadingDialog ZHI_LINK_DIALOG = new LoadingDialog();
    }

    private ThreeBallRotationProgressBar loading;

    private CustomDialog dialog;

    public LoadingDialog showDialog(Object title, Activity activity) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                .view(R.layout.dialog_loading)
                .style(R.style.LoadingDialogStyle)
                .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.65))
                .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);
            loading = (ThreeBallRotationProgressBar) builder.getView(R.id.loading);
            loading.setDistance(50).setMaxRadius(20).setMinRadius(10).setDuration(2000);
            loading.startAnimator();
            dialog = builder.build();
            dialog.show();
        }
        return MainHolder.ZHI_LINK_DIALOG;
    }

    /**
     * 隐藏等待dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            if (null != loading) {
                loading.stopAnimator();
            }
            dialog.dismiss();
            dialog = null;

        }
    }

    public LoadingDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public LoadingDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

}
