package com.zhilink.common.uitls;

import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * 震动提醒
 *
 * @author xiemeng
 * @date 2018-10-31
 */
public class VibratorUtil {
    private static final int VIBRATE_TIME = 1000;

    public static void vibrate(final Context context) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (null != vib && vib.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect vibrationEffect = VibrationEffect.createOneShot(VIBRATE_TIME, VibrationEffect.DEFAULT_AMPLITUDE);
                vib.vibrate(vibrationEffect);
            } else {
                vib.vibrate(VIBRATE_TIME);
            }
        }

    }

}
