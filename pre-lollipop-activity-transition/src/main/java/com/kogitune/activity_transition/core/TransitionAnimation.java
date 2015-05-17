package com.kogitune.activity_transition.core;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by takam on 2015/05/17.
 */
public class TransitionAnimation {
    private static final String TAG = "Transition";
    public static WeakReference<Bitmap> bitmapCache;

    public static MoveData startAnimation(Context context, final View toView, Bundle transitionBundle, Bundle savedInstanceState, final int duration, final TimeInterpolator interpolator) {
        final TransitionData transitionData = new TransitionData(context, transitionBundle);
        if (transitionData.imageFilePath != null) {
            setImageToView(toView, transitionData.imageFilePath);
        }
        final MoveData moveData = new MoveData();
        moveData.toView = toView;
        moveData.duration = duration;
        if (savedInstanceState == null) {

            ViewTreeObserver observer = toView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    toView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    toView.getLocationOnScreen(screenLocation);
                    moveData.leftDelta = transitionData.thumbnailLeft - screenLocation[0];
                    moveData.topDelta = transitionData.thumbnailTop - screenLocation[1];

                    moveData.widthScale = (float) transitionData.thumbnailWidth / toView.getWidth();
                    moveData.heightScale = (float) transitionData.thumbnailHeight / toView.getHeight();

                    runEnterAnimation(moveData, interpolator);

                    return true;
                }
            });
        }
        return moveData;
    }


    private static void runEnterAnimation(MoveData moveData, TimeInterpolator interpolator) {
        final View toView = moveData.toView;
        toView.setPivotX(0);
        toView.setPivotY(0);
        toView.setScaleX(moveData.widthScale);
        toView.setScaleY(moveData.heightScale);
        toView.setTranslationX(moveData.leftDelta);
        toView.setTranslationY(moveData.topDelta);

        toView.animate().setDuration(moveData.duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(interpolator);
    }

    private static void setImageToView(View toView, String imageFilePath) {
        Bitmap bitmap;
        if (bitmapCache == null || (bitmap = bitmapCache.get()) == null) {
            // Cant get bitmap by static field
            bitmap = BitmapFactory.decodeFile(imageFilePath);
        } else {
            bitmapCache.clear();
        }
        if (toView instanceof ImageView) {
            final ImageView toImageView = (ImageView) toView;
            toImageView.setImageBitmap(bitmap);
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                toView.setBackground(new BitmapDrawable(toView.getResources(), bitmap));
            } else {
                toView.setBackgroundDrawable(new BitmapDrawable(toView.getResources(), bitmap));
            }
        }
    }

    public static void startExitAnimation(MoveData moveData, final Runnable endAction) {
        View view = moveData.toView;
        int duration = moveData.duration;
        int leftDelta = moveData.leftDelta;
        int topDelta = moveData.topDelta;
        float widthScale = moveData.widthScale;
        float heightScale = moveData.heightScale;
        view.animate().setDuration(duration).
                scaleX(widthScale).scaleY(heightScale).
                translationX(leftDelta).translationY(topDelta);
        view.postDelayed(endAction, duration);
    }
}
