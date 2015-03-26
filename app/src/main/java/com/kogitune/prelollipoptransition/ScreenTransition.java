package com.kogitune.prelollipoptransition;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by takam on 2015/03/26.
 */
public class ScreenTransition {
    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final int ANIM_DURATION = 500;
    private final Activity activity;
    private View toView;
    private Intent fromIntent;
    private int mOriginalOrientation;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    public ScreenTransition(Activity activity) {
        this.activity = activity;
    }

    public static ScreenTransition with(Activity activity) {
        return new ScreenTransition(activity);
    }

    public ScreenTransition to(View toView) {
        this.toView = toView;
        return this;
    }

    public ScreenTransition fromIntent(Intent intent) {
        this.fromIntent = intent;
        return this;
    }

    public void start(Bundle savedInstanceState) {
        final Bundle bundle = fromIntent.getExtras();
        String description = bundle.getString(BuildConfig.APPLICATION_ID + ".description");
        final int thumbnailTop = bundle.getInt(BuildConfig.APPLICATION_ID + ".top");
        final int thumbnailLeft = bundle.getInt(BuildConfig.APPLICATION_ID + ".left");
        final int thumbnailWidth = bundle.getInt(BuildConfig.APPLICATION_ID + ".width");
        final int thumbnailHeight = bundle.getInt(BuildConfig.APPLICATION_ID + ".height");
        mOriginalOrientation = bundle.getInt(BuildConfig.APPLICATION_ID + ".orientation");
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

    private Bitmap loadImageFromFile(){
        File imageFile = new File(activity.getFilesDir(), ScreenTransitionLauncher.TEMP_IMAGE_FILE_NAME);
        FileOutputStream out;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
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
