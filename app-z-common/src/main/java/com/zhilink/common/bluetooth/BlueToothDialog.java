package com.zhilink.common.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhilink.common.R;
import com.zhilink.common.dialogs.LoadingDialog;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.poplibrary.ToastUtils;
import com.zhilink.utils.ViewUtils;

import java.util.List;

/**
 * 蓝牙选择
 *
 * @author xiemeng
 * @date 2018-11-6 11:36
 */
public class BlueToothDialog {
    private static final String TAG = BlueToothDialog.class.getSimpleName();

    private BlueToothDialog() {
    }

    public static BlueToothDialog getInstance() {
        return BlueToothDialog.MainHolder.ZHI_LINK_DIALOG;
    }

    private static class MainHolder {
        private static final BlueToothDialog ZHI_LINK_DIALOG = new BlueToothDialog();
    }

    public interface ShowListListener {
        /**
         * 点击
         *
         * @param position          点击位置
         * @param blueToothShowBean 当前文本
         */
        void onSure(int position, BlueToothShowBean blueToothShowBean);

        /**
         * 连接失败
         *
         * @param msg 错误描述
         */
        void onFailed(String msg);
    }

    private CustomDialog dialog;

    public void showListDialog(final List<BlueToothShowBean> strings, final Activity activity, final ShowListListener listener) {
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
                    .cancelTouchOut(true).backCancelTouchOut(true);
            RecyclerView rvList = (RecyclerView) builder.getView(R.id.rv_list);
            rvList.setLayoutManager(new LinearLayoutManager(activity));
            ShowAdapter adapter = new ShowAdapter(activity, strings);
            rvList.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    clickItem(adapter, activity, position, strings, listener);
                }
            });
            TextView tvTitle = (TextView) builder.getView(R.id.tv_title);
            tvTitle.setText(R.string.choose_device);
            dialog = builder.build();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    listener.onFailed(activity.getString(R.string.connect_device_failed));
                }
            });
            dialog.show();
        }
    }


    /**
     * 点击一个设备进行连接
     */

    private void clickItem(final BaseQuickAdapter adapter, final Activity activity, final int pos, final List<BlueToothShowBean> strings, final ShowListListener listener) {
        LoadingDialog.getInstance().showDialog(null, activity);
        BlueToothShowBean temp = strings.get(pos);
        BlueToothManager.getInstance().connect(temp, new BlueToothManager.ConnectListener() {
            @Override
            public void onSuccess() {
                LoadingDialog.getInstance().dismissDialog();
                try {
                    ToastUtils.showShort(activity, R.string.connect_device_ok);
                    strings.get(pos).setConnected(BlueToothShowBean.STATE_RIGHT);
                    listener.onSure(pos, strings.get(pos));
                    dismissDialog();
                } catch (Exception e) {
                    strings.get(pos).setConnected(BlueToothShowBean.STATE_ERROR);
                    listener.onFailed(activity.getString(R.string.connect_device_failed));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed() {
                adapter.notifyDataSetChanged();

                LoadingDialog.getInstance().dismissDialog();
                strings.get(pos).setConnected(BlueToothShowBean.STATE_ERROR);
                listener.onFailed(activity.getString(R.string.connect_device_failed));
            }
        });
    }

    class ShowAdapter extends BaseQuickAdapter<BlueToothShowBean, BaseViewHolder> {
        private Context mContext;

        private ShowAdapter(Context context, @Nullable List<BlueToothShowBean> data) {
            super(R.layout.rv_blue_tooth_list, data);
            mContext = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, BlueToothShowBean item) {
            helper.setText(R.id.tv_device_name, item.getDeviceName())
                    .setText(R.id.tv_address, item.getAddress());
            if (BlueToothShowBean.STATE_RIGHT == item.getConnected()) {
                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(mContext, R.color.main_color))
                        .setTextColor(R.id.tv_address, ContextCompat.getColor(mContext, R.color.main_color))
                        .setImageResource(R.id.iv_connected, R.drawable.connect_right);
            } else if (BlueToothShowBean.STATE_ERROR == item.getConnected()) {
                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(mContext, R.color.main_local_use_color))
                        .setTextColor(R.id.tv_address, ContextCompat.getColor(mContext, R.color.main_local_use_color))
                        .setImageResource(R.id.iv_connected, R.drawable.connect_error);
            } else if (BlueToothShowBean.STATE_NEVER == item.getConnected()) {
                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(mContext, R.color.main_local_use_color))
                        .setTextColor(R.id.tv_address, ContextCompat.getColor(mContext, R.color.main_local_use_color))
                        .setImageResource(R.id.iv_connected, 0);
            }
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

}
