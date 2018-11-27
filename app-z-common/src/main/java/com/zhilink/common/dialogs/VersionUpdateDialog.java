package com.zhilink.common.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhilink.common.R;
import com.zhilink.common.base.BaseApplication;
import com.zhilink.common.bean.AppVersionUpdateRespBean;
import com.zhilink.common.uitls.Constant;
import com.zhilink.common.view.DownloadProgressBar;
import com.zhilink.poplibrary.CustomDialog;
import com.zhilink.utils.StringUtils;
import com.zhilink.utils.ViewUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 版本更新
 *
 * @author xiemeng
 * @date 2018-11-20 16:00
 */
public class VersionUpdateDialog {

    private boolean mCancelTouchOut = false;

    private boolean mBackCancelTouchOut = true;

    private OkHttpClient okHttpClient;

    private int complete = 100;

    private Disposable subscribe;

    private VersionUpdateDialog() {
    }

    public static VersionUpdateDialog getInstance() {
        return VersionUpdateDialog.MainHolder.ZHI_LINK_DIALOG;
    }

    private CustomDialog dialog;

    private static class MainHolder {
        private static final VersionUpdateDialog ZHI_LINK_DIALOG = new VersionUpdateDialog();
    }

    public interface VersionUpdateListener {
        /**
         * 下载进度
         *
         * @param pro 进度
         */
        void showProgress(int pro);
    }

    public void showVersionUpdate(final Activity activity, final AppVersionUpdateRespBean versionUpdateRespBean, final VersionUpdateListener listener) {
        okHttpClient = ProgressManager.getInstance().with(new OkHttpClient.Builder())
            .build();
        if (activity != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(activity)
                .view(R.layout.dialog_version_update)
                .style(R.style.CustomDialog)
                .widthPx((int) (ViewUtils.getScreenWidth(activity) * 0.65))
                .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setViewText(R.id.tv_version_name, versionUpdateRespBean.getVersionName())
                .setViewText(R.id.tv_version_info, versionUpdateRespBean.getUpdateInfo())
                .cancelTouchOut(mCancelTouchOut).backCancelTouchOut(mBackCancelTouchOut);

            TextView tvVersionInfo = (TextView) builder.getView(R.id.tv_version_info);
            tvVersionInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
            final DownloadProgressBar dpProgress = (DownloadProgressBar) builder.getView(R.id.dp_progress);
            final TextView tvDownload = (TextView) builder.getView(R.id.tv_download);
            dpProgress.setMaxCount(100);
            final String apkPath = Constant.LocalFilePath.APK_PATH;
            File file2 = new File(apkPath);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            ProgressManager.getInstance().addResponseListener(versionUpdateRespBean.getDownloadUrl(), new ProgressListener() {
                @Override
                public void onProgress(ProgressInfo progressInfo) {
                    int progress = progressInfo.getPercent();
                    String msg = progress + "%";
                    tvDownload.setText(msg);
                    dpProgress.setCurrentCount(progress);
                    if (progress == complete) {
                        tvDownload.setEnabled(true);
                        tvDownload.setText(R.string.download_finish);
                        dismissDialog();
                        //下载完成自动安装apk
                        File file = new File(apkPath);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        activity.startActivity(intent);
                    }
                }

                @Override
                public void onError(long id, Exception e) {
                    tvDownload.setEnabled(true);
                    String msg = StringUtils.isBlank(e.getMessage()) ? e.getMessage() : e.getCause().toString();
                    FailedDialog.getInstance().backCancelTouchOut(true).cancelTouchOut(true).showFailedDialog(msg, activity, new FailedDialog.ClickListener() {
                        @Override
                        public void onSure() {

                        }
                    });
                }
            });
            builder.addViewOnclick(R.id.tv_download, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvDownload.setEnabled(false);
                    downloadStart(activity, versionUpdateRespBean.getDownloadUrl());
                }
            });
            dialog = builder.build();
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (null != subscribe && subscribe.isDisposed()) {
                        subscribe.dispose();
                    }
                }
            });
        }

    }


    /**
     * 点击开始下载资源
     */
    private void downloadStart(final Activity activity, final String url) {
        subscribe = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> str) {
                download(url);
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }

    private void download(String url) {
        try {
            Request request = new Request.Builder()
                .url(url)
                .build();

            Response response = okHttpClient.newCall(request).execute();
            InputStream is = response.body().byteStream();
            String apkPath = Constant.LocalFilePath.APK_PATH;
            String newAppName = BaseApplication.getInstance().getString(R.string.app_name) + ".apk";


            File file = new File(apkPath + "/" + newAppName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024 * 2];
            int length;
            while ((length = is.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
            ProgressManager.getInstance().notifyOnErorr(url, e);
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

    public VersionUpdateDialog cancelTouchOut(boolean cancelTouchOut) {
        this.mCancelTouchOut = cancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

    public VersionUpdateDialog backCancelTouchOut(boolean backCancelTouchOut) {
        this.mBackCancelTouchOut = backCancelTouchOut;
        return MainHolder.ZHI_LINK_DIALOG;
    }

}
