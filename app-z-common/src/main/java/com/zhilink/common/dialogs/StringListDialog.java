package com.zhilink.common.dialogs;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhilink.common.R;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.utils.ViewUtils;

import java.util.List;

/**
 * string List对话框
 *
 * @author xiemeng
 * @date 2018-9-30 11:13
 */

public class StringListDialog {
    private boolean mCancelTouchOut=true;

    private boolean mBackCancelTouchOut=true;

    private StringListDialog() {
    }

    public static StringListDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private static class MainHolder {
        private static final StringListDialog ZHI_LINK_DIALOG = new StringListDialog();
    }


    public interface ShowListListener {
        /**
         * 点击
         *
         * @param position 点击位置
         * @param text     当前文本
         */
        void onSure(int position, String text);
    }

    private CustomDialog dialog;

    public StringListDialog showListDialog(Object title, final List<String> strings, Activity activity, final ShowListListener listener) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                    .view(R.layout.dialog_string_list)
                    .style(R.style.CustomDialog)
                    .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.8))
                    .heightPx((int) (ViewUtils.getScreenHeight(activity) * 0.5))
                    .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);
            RecyclerView rvList = (RecyclerView) builder.getView(R.id.rv_list);
            rvList.setLayoutManager(new LinearLayoutManager(activity));
            StringAdapter adapter = new StringAdapter(strings);
            rvList.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    listener.onSure(position, strings.get(position));
                    dialog.dismiss();
                }
            });
            TextView tvTitle = (TextView) builder.getView(R.id.tv_title);
            if (title instanceof Integer) {
                tvTitle.setText((Integer) title);
            } else {
                if (null != title) {
                    tvTitle.setText(title.toString());
                }
            }
            dialog = builder.build();
            dialog.show();
        }
        return MainHolder.ZHI_LINK_DIALOG;
    }


    class StringAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        private StringAdapter(@Nullable List<String> data) {
            super(R.layout.rv_string_list, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_item, item);
        }
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
    public StringListDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public StringListDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

}
