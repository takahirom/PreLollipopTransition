package com.kogitune.activity_transition;

import android.animation.TimeInterpolator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by takam on 2015/03/26.
 */
public class ActivityTransition {
    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final int ANIM_DURATION = 500;
    private View toView;
    private Intent fromIntent;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    public ActivityTransition(Intent intent) {
        this.fromIntent = intent;
    }

    public static ActivityTransition with(Intent intent) {
        return new ActivityTransition(intent);
    }

    public ActivityTransition to(View toView) {
        this.toView = toView;
        return this;
    }


    public void start(Bundle savedInstanceState) {
        final Bundle bundle = fromIntent.getExtras();
        final int thumbnailTop = bundle.getInt(BuildConfig.APPLICATION_ID + ".top");
        final int thumbnailLeft = bundle.getInt(BuildConfig.APPLICATION_ID + ".left");
        final int thumbnailWidth = bundle.getInt(BuildConfig.APPLICATION_ID + ".width");
        final int thumbnailHeight = bundle.getInt(BuildConfig.APPLICATION_ID + ".height");
        if (savedInstanceState == null) {

            ViewTreeObserver observer = toView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    toView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    toView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    mWidthScale = (float) thumbnailWidth / toView.getWidth();
                    mHeightScale = (float) thumbnailHeight / toView.getHeight();

                    runEnterAnimation();

                    return true;
                }
            });
        }
    }

    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);

        toView.setPivotX(0);
        toView.setPivotY(0);
        toView.setScaleX(mWidthScale);
        toView.setScaleY(mHeightScale);
        toView.setTranslationX(mLeftDelta);
        toView.setTranslationY(mTopDelta);

        toView.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator);
    }
}
