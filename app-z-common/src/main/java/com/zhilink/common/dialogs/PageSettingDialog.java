package com.zhilink.common.dialogs;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.zhilink.common.R;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.utils.LogUtils;
import com.zhilink.utils.ViewUtils;

/**
 * 数字选择器
 *
 * @author xiemeng
 * @date 2018-10-31
 */

public class PageSettingDialog {

    private boolean mCancelTouchOut=true;

    private boolean mBackCancelTouchOut=true;

    private PageSettingDialog() {
    }

    public static PageSettingDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private static class MainHolder {
        private static final PageSettingDialog ZHI_LINK_DIALOG = new PageSettingDialog();
    }


    public interface ClickListener {
        /**
         * 确定
         *
         * @param num 选择数字
         */
        void onSure(int num);

    }

    private CustomDialog dialog;

    public PageSettingDialog showDialog(int mini, int max, int old, Activity activity, final ClickListener listener) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                    .view(R.layout.dialog_page_setting)
                    .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.65))
                    .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .style(R.style.CustomDialog)
                    .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);
            final NumberPicker numPicker = (NumberPicker) builder.getView(R.id.num_picker);
            numPicker.setMinValue(mini);
            numPicker.setMaxValue(max);
            numPicker.setValue(old);
            numPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                }
            });
            builder.addViewOnclick(R.id.btn_sure, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onSure(numPicker.getValue());
                    }
                    dialog.dismiss();
                }
            });
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

    public PageSettingDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public PageSettingDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }
}
