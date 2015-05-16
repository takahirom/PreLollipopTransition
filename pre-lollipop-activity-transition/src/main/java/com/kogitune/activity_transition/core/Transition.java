package com.kogitune.activity_transition.core;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.kogitune.activity_transition.BuildConfigUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by takam on 2015/05/17.
 */
public class Transition {
    private static final String TAG = "Transition";
    public static final String TEMP_IMAGE_FILE_NAME = "activity_transition_image.png";
    public static WeakReference<Bitmap> bitmapCache;

    public static Bundle createTransitionBundle(Context context, View fromView, Bitmap bitmap) {
        // Bitmap is Optional
        String imageFilePath = null;
        if (bitmap != null) {
            imageFilePath = saveImage(context, bitmap);
        }
        int[] screenLocation = new int[2];
        fromView.getLocationOnScreen(screenLocation);
        final TransitionData transitionData = new TransitionData(context, screenLocation[0], screenLocation[1], fromView.getMeasuredWidth(), fromView.getMeasuredHeight(), imageFilePath);
        return transitionData.getBundle();
    }

    private static String saveImage(Context context, Bitmap bitmap) {
        new File(context.getFilesDir().getAbsolutePath() + "/activity_transition/").mkdirs();
        final File imageFile = new File(context.getFilesDir().getAbsolutePath() + "/activity_transition/", TEMP_IMAGE_FILE_NAME);
        final String imageFilePath = imageFile.getAbsolutePath();
        final Boolean isDebug = (Boolean) BuildConfigUtils.getBuildConfigValue(context, "DEBUG");

        BufferedOutputStream bos = null;
        try {
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
            bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        } catch (FileNotFoundException e) {
            if (isDebug) {
                Log.i(TAG, "file not found", e);
            }
        } catch (IOException e) {
            if (isDebug) {
                Log.i(TAG, "can't create file", e);
            }
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
                if (isDebug) {
                    //IOException, NullPointerException
                    Log.i(TAG, "fail save image", e);
                }
            }
        }
        bitmapCache = new WeakReference<Bitmap>(bitmap);
        return imageFilePath;
    }

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
