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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 开始日期，结束日期选择器
 *
 * @author xiemeng
 * @date 2018-10-18 14:04
 */

public class TwoDatePickerDialog {

    private boolean mCancelTouchOut = true;

    private boolean mBackCancelTouchOut = true;

    private TwoDatePickerDialog() {
    }

    public static TwoDatePickerDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private CustomDialog dialog;

    private static class MainHolder {
        private static final TwoDatePickerDialog ZHI_LINK_DIALOG = new TwoDatePickerDialog();
    }

    public interface GetDateListener {
        /**
         * 显示日期
         *
         * @param startDate 开始
         * @param endDate   结束
         * @param show      展示
         */
        void showDate(String startDate, String endDate, String show);

        /**
         * 取消
         */
        void cancel();
    }

    public TwoDatePickerDialog openDatePicker(final Activity activity, final GetDateListener listener) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                .view(R.layout.dialog_two_date_picker)
                .style(R.style.CustomDialog)
                .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.7))
                .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                .addViewOnclick(R.id.btn_clear, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissDialog();
                        if (null != listener) {
                            listener.showDate("", "", "");
                        }
                    }
                })
                .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);
            final DatePicker dpStart = (DatePicker) builder.getView(R.id.dp_start);
            final DatePicker dpEnd = (DatePicker) builder.getView(R.id.dp_end);
            initDate(dpStart);
            initDate(dpEnd);
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
                    String startDate = formatDate(dpStart);
                    String endDate = formatDate(dpEnd);
                    try {
                        @SuppressLint("SimpleDateFormat")
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date dt1 = df.parse(startDate);
                        Date dt2 = df.parse(endDate);
                        if (dt1.getTime() > dt2.getTime()) {
                            FailedDialog.getInstance().showFailedDialog(R.string.start_date_before_end_date, activity, null);
                        } else {
                            //日期分隔
                            String showSplit = "~";
                            dismissDialog();
                            if (null != listener) {
                                listener.showDate(startDate, endDate, startDate + showSplit + endDate);
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
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

    private void initDate(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

            }
        });
    }

    /**
     * 隐藏等待dialog
     */
    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;

        }
    }

    public TwoDatePickerDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public TwoDatePickerDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }
}
