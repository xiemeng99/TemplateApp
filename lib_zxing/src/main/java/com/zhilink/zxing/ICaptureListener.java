package com.zhilink.zxing;

import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;
import com.zhilink.zxing.android.ViewfinderView;
import com.zhilink.zxing.android.camera.CameraManager;

/**
 * 扫描二维码实现该接口
 *
 * @author xiemeng
 * @date 2018-11-5 11:30
 */
public interface ICaptureListener {
    Handler getHandler();

    CameraManager getCameraManager();

    ViewfinderView getViewfinderView();

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor);


    void drawViewfinder();
}
