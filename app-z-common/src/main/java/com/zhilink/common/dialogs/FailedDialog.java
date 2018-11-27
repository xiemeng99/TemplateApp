package com.zhilink.common.dialogs;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhilink.common.R;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.utils.ViewUtils;

/**
 * 失败弹窗
 *
 * @author xiemeng
 * @date 2018-9-30 11:13
 */

public class FailedDialog {
    private boolean mCancelTouchOut;

    private boolean mBackCancelTouchOut;


    private FailedDialog() {
    }

    public static FailedDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private static class MainHolder {
        private static final FailedDialog ZHI_LINK_DIALOG = new FailedDialog();
    }


    public interface ClickListener {
        /**
         * 确定
         */
        void onSure();

    }

    private CustomDialog dialog;

    public FailedDialog showFailedDialog(Object title, Activity activity, final ClickListener listener) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                .view(R.layout.dialog_failed)
                .style(R.style.CustomDialog)
                .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.65))
                .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                .addViewOnclick(R.id.btn_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onSure();
                        }
                        dialog.dismiss();
                    }
                })
                .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);

            TextView tvMsg = (TextView) builder.getView(R.id.tv_msg);
            tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
            if (title instanceof Integer) {
                tvMsg.setText((Integer) title);
            } else {
                if (null != title) {
                    tvMsg.setText(title.toString());
                }
            }
            try {
                dialog = builder.build();
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return MainHolder.ZHI_LINK_DIALOG;
    }


    /**
     * 隐藏等待dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;

        }
    }

    public FailedDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public FailedDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

}
