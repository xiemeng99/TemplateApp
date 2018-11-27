package com.zhilink.common.dialogs;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhilink.common.R;
import com.zhilink.common.view.ViewVisibleAnimator;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.utils.StringUtils;
import com.zhilink.utils.ViewUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 成功弹窗
 *
 * @author xiemeng
 * @date 2018-9-30 11:13
 */

public class SuccessDialog {

    private boolean mCancelTouchOut;

    private boolean mBackCancelTouchOut;

    private SuccessDialog() {
    }

    public static SuccessDialog getInstance() {
        return MainHolder.ZHI_LINK_DIALOG;
    }

    private static class MainHolder {
        private static final SuccessDialog ZHI_LINK_DIALOG = new SuccessDialog();
    }


    public interface ClickListener {
        /**
         * 确定
         */
        void onSure();

    }

    private CustomDialog dialog;

    public SuccessDialog showSuccessDialog(Object title, Activity activity, final ClickListener listener) {
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                .view(R.layout.dialog_success)
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
            final View rlImg = builder.getView(R.id.rl_img);
            final View rlResult = builder.getView(R.id.rl_result);
            rlResult.setVisibility(View.GONE);

            ViewVisibleAnimator.getInstance().animateUp(rlImg, 500, new ViewVisibleAnimator.AnimationListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onEnd() {
                    Disposable subscribe = Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(ObservableEmitter<String> e) {
                            e.onNext("");
                        }
                    }).subscribeOn(Schedulers.io())
                        .delay(100, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(final String s) {
                                rlImg.setVisibility(View.GONE);
                                ViewVisibleAnimator.getInstance().animateUp(rlResult, 500, null);
                                rlResult.setVisibility(View.VISIBLE);
                            }
                        });
                }
            });
            TextView tvMsg = (TextView) builder.getView(R.id.tv_msg);
            tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
            if (title instanceof Integer) {
                tvMsg.setText((Integer) title);
            } else {
                if (null != title) {
                    tvMsg.setText(StringUtils.isBlank(title.toString()) ? activity.getString(R.string.commit_success) : title.toString());
                }
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

    public SuccessDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public SuccessDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

}
