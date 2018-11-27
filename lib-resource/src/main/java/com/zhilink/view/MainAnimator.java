package com.zhilink.view;

import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * 首页动画
 *
 * @author xiemeng
 * @date 2018-11-7 09:15
 */
public class MainAnimator {


    /**
     * 从底部进入
     * @return 动画
     */
    public static AnimationSet getAnimationSetFromBottom() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateX1 = new TranslateAnimation(RELATIVE_TO_SELF, 2.5f, RELATIVE_TO_SELF, 0,
                RELATIVE_TO_SELF, 2.5f, RELATIVE_TO_SELF, 0);
        translateX1.setDuration(400);
        translateX1.setInterpolator(new DecelerateInterpolator());
        translateX1.setStartOffset(0);
        animationSet.addAnimation(translateX1);
        animationSet.setDuration(400);
        return animationSet;
    }

    /**
     * 播放动画
     */
    public static void playLayoutAnimation(Animation animation, RecyclerView recyclerView, boolean isReverse) {
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation);
        controller.setColumnDelay(0.2f);
        controller.setRowDelay(0.3f);
        controller.setOrder(isReverse ? LayoutAnimationController.ORDER_REVERSE : LayoutAnimationController.ORDER_NORMAL);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
