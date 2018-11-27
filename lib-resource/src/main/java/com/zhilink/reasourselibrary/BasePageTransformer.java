package com.zhilink.reasourselibrary;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * viewpager动画
 * copy from https://github.com/leifu1107/ViewpagerTransformer
 * @author xiemeng
 * @date 2018-11-2 10:48
 */
public abstract class BasePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        if (position < -1.0f) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            handleInvisiblePage(view, position);
        } else if (position <= 0.0f) {
            // [-1,0]
            // Use the default slide transition when moving to the left page
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            // (0,1]
            handleRightPage(view, position);
        } else {
            // (1,+Infinity]
            // This page is way off-screen to the right.
            handleInvisiblePage(view, position);
        }
    }

    public abstract void handleInvisiblePage(View view, float position);

    public abstract void handleLeftPage(View view, float position);

    public abstract void handleRightPage(View view, float position);


}