package com.zhilink.common.dialogs;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhilink.common.R;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.utils.ViewUtils;

/**
 * 确定取消对话框
 *
 * @author xiemeng
 * @date 2018-9-30 11:13
 */

public class ZhiLinkDialog {

    private ZhiLinkDialog() {
    }

    private boolean mCancelTouchOut;

    private boolean mBackCancelTouchOut;

    public static ZhiLinkDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private static class MainHolder {
        private static final ZhiLinkDialog ZHI_LINK_DIALOG = new ZhiLinkDialog();
    }


    public interface ClickListener {
        /**
         * 确定
         */
        void onSure();

        /**
         * 取消
         */
        void cancel();
    }

    private CustomDialog dialog;

    public ZhiLinkDialog showDialog(Object title, Activity activity, final ClickListener listener) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                .view(R.layout.dialog_zhilink)
                .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.65))
                .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                .style(R.style.CustomDialog)
                .addViewOnclick(R.id.btn_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onSure();
                        }
                        dialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.btn_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.cancel();
                        }
                        dialog.dismiss();
                    }
                })
                .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);

            TextView tvTitle = (TextView) builder.getView(R.id.tv_title);
            if (title instanceof Integer) {
                tvTitle.setText((Integer) title);
            } else {
                tvTitle.setText(title.toString());
            }
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
            dialog.dismiss();
            dialog = null;

        }
    }

    public ZhiLinkDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public ZhiLinkDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }
}
