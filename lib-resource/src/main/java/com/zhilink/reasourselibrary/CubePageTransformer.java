package com.zhilink.reasourselibrary;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * 首页立方体动画
 * copy from https://github.com/leifu1107/ViewpagerTransformer
 *
 * @author xiemeng
 * @date 2018-11-2 10:49
 */
public class CubePageTransformer extends BasePageTransformer {
    private float mMaxRotation = 90.0f;

    public CubePageTransformer() {
    }

    public CubePageTransformer(float maxRotation) {
        setMaxRotation(maxRotation);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewCompat.setPivotX(view, view.getMeasuredWidth());
        ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewCompat.setPivotX(view, view.getMeasuredWidth());
        ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view, mMaxRotation * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewCompat.setPivotX(view, 0);
        ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view, mMaxRotation * position);
    }

    public void setMaxRotation(float maxRotation) {
        if (maxRotation >= 0.0f && maxRotation <= 90.0f) {
            mMaxRotation = maxRotation;
        }
    }

}