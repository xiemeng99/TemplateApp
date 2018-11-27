package com.zhilink.common.zxing;

import android.app.Activity;
import android.content.Intent;

import com.zhilink.zxing.camera.GetBarCodeListener;


/**
 * 二维码扫描
 * Initial the camera
 * @author xiemeng
 */
public class MipCaActivityCapture {

    /**
     * 扫码监听
     */
    public static void startCameraActivity(Activity activity, GetBarCodeListener listener) {
        if (null != activity) {
            Intent intent = new Intent();
            intent.setClass(activity, CaptureScanActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            CaptureScanActivity.mBarcodeListener = listener;
            activity.startActivity(intent);
        }
    }


}