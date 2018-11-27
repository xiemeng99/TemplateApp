package com.zhilink.common.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.zhilink.common.R;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.utils.ViewUtils;

/**
 * 单个日期选择器
 *
 * @author xiemeng
 * @date 2018-10-18 14:04
 */

public class DatePickerDialog {
    private boolean mCancelTouchOut=true;

    private boolean mBackCancelTouchOut=true;

    private DatePickerDialog() {
    }

    public static DatePickerDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private CustomDialog dialog;

    private static class MainHolder {
        private static final DatePickerDialog ZHI_LINK_DIALOG = new DatePickerDialog();
    }

    public interface GetDateListener {
        /**
         * 显示日期
         *
         * @param date 日期
         */
        void showDate(String date);

        /**
         * 取消
         */
        void cancel();
    }

    public DatePickerDialog openDatePicker(Activity activity, final GetDateListener listener) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                .view(R.layout.dialog_date_picker)
                .style(R.style.CustomDialog)
                .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.7))
                .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                .addViewOnclick(R.id.btn_clear, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissDialog();
                        if (null != listener) {
                            listener.showDate("");
                        }
                    }
                })
                .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);
            final DatePicker dpDate = (DatePicker) builder.getView(R.id.dp_date);
            Button btnCancel = (Button) builder.getView(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                    if (null != listener) {
                        listener.cancel();
                    }
                }
            });
            Button btnSure = (Button) builder.getView(R.id.btn_sure);
            btnSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                    String startDate = formatDate(dpDate);
                    if (null != listener) {
                        listener.showDate(startDate);
                    }
                }
            });

            dialog = builder.build();
            dialog.show();
        }
        return MainHolder.ZHI_LINK_DIALOG;
    }


    private String formatDate(DatePicker datePicker) {
        StringBuilder date = new StringBuilder();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();
        //日期分隔
        String dateSplit = "-";
        date.append(year)
            .append(dateSplit)
            .append(format2Digits(month + 1))
            .append(dateSplit)
            .append(format2Digits(dayOfMonth))
        ;
        return date.toString();
    }

    @SuppressLint("DefaultLocale")
    private String format2Digits(int value) {
        return String.format("%02d", value);
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


    public DatePickerDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public DatePickerDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }
}
