package com.kogitune.activity_transition;

import android.animation.Animator;

/**
 * Created by Joe on 2016/10/8.
 * Email lovejjfg@gmail.com
 */

public abstract class SimpleAnimatorListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public abstract void onAnimationEnd(Animator animation);

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
