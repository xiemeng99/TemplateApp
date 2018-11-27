package com.zhilink.common.secondbase;

import android.widget.TextView;

import com.zhilink.common.base.BaseTitleActivity;
import com.zhilink.common.dialogs.DatePickerDialog;
import com.zhilink.utils.ViewUtils;

/**
 * 基础界面
 * 包含两个fg
 *
 * @author xiemeng
 * @date 2018-10-10 09:11
 */

public abstract class BaseLinkActivity extends BaseTitleActivity {

    @Override
    public boolean isShowMessage() {
        return true;
    }

    @Override
    public boolean isShowScan() {
        return true;
    }

    @Override
    public boolean isShowSearch() {
        return false;
    }

    @Override
    public boolean isHasExitDialog() {
        return false;
    }



    /**
     * 批次
     */
    protected void setDate(final TextView tvDate) {
        ViewUtils.getViewRightListener(tvDate, new ViewUtils.ClickListener() {
            @Override
            public void click() {
                DatePickerDialog.getInstance().openDatePicker(activity, new DatePickerDialog.GetDateListener() {
                    @Override
                    public void showDate(String date) {
                        tvDate.setText(date);
                    }

                    @Override
                    public void cancel() {
                    }
                });
            }
        });
    }

}
