package com.zhilink.common.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * view显示隐藏动画
 *
 * @author xiemeng
 * @date 2018-10-11 19:20
 */

public class ViewVisibleAnimator {
    /**
     * 动画时间
     */
    public static final int DURATION_TIME = 300;


    private ViewVisibleAnimator() {
    }

    public static ViewVisibleAnimator getInstance() {
        return ViewHolder.HOLDER;
    }

    private static class ViewHolder {
        private static final ViewVisibleAnimator HOLDER = new ViewVisibleAnimator();
    }

    /**
     * 打开动画,从上到下
     *
     * @param view view
     */
    public void animateOpen(View view) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        view.startAnimation(mShowAction);
    }

    /**
     * 关闭 从下到上隐藏
     *
     * @param view view
     */
    public void animateClose(View view) {

        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(500);
        view.startAnimation(mHiddenAction);
        view.setVisibility(View.GONE);
    }

    public interface AnimationListener {
        /**
         * 动画开始
         */
        void onStart();

        /**
         * 动画结束
         */
        void onEnd();
    }

    /**
     * 从上到下
     *
     * @param view view
     */
    public void animateDown(final View view, int duration, final AnimationListener listener) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mShowAction.setDuration(duration);
        view.startAnimation(mShowAction);
        if (null != listener) {
            mShowAction.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    listener.onStart();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.clearAnimation();
                    listener.onEnd();
                }
            });
        }
    }

    /**
     * 从下到上
     *
     * @param view view
     */
    public void animateUp(final View view, int duration, final AnimationListener listener) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(duration);
        view.startAnimation(mHiddenAction);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (null != listener) {
                    listener.onStart();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.clearAnimation();
                if (null != listener) {
                    listener.onEnd();
                }
            }
        });
    }


    /**
     * 到左
     *
     * @param view view
     */
    public void animateLeft(final View view, int duration, final AnimationListener listener) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(duration);
        view.startAnimation(mShowAction);
        mShowAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (null != listener) {
                    listener.onStart();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                if (null != listener) {
                    listener.onEnd();
                }
            }
        });
    }

    /**
     * 到右
     *
     * @param view view
     */
    public void animateRight(View view, int duration, final AnimationListener listener) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(duration);
        view.startAnimation(mShowAction);
        mShowAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (null != listener) {
                    listener.onStart();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (null != listener) {
                    listener.onEnd();
                }
            }
        });
    }
}


