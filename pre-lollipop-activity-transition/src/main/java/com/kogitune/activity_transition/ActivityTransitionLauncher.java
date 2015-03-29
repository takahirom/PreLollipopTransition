package com.kogitune.activity_transition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by takam on 2015/03/26.
 */
public class ActivityTransitionLauncher {
    private static final String TAG = "TransitionLauncher";
    private static final String TEMP_IMAGE_FILE_NAME = "activity_transition_image.png";
    public static final String EXTRA_IMAGE_LEFT = BuildConfig.APPLICATION_ID + ".left";
    public static final String EXTRA_IMAGE_TOP = BuildConfig.APPLICATION_ID + ".top";
    public static final String EXTRA_IMAGE_WIDTH = BuildConfig.APPLICATION_ID + ".width";
    public static final String EXTRA_IMAGE_HEIGHT = BuildConfig.APPLICATION_ID + ".height";
    public static final String EXTRA_IMAGE_PATH = BuildConfig.APPLICATION_ID + ".imageFilePath";

    private final Activity activity;
    private View fromView;
    private String imageFilePath = null;
    public static WeakReference<Bitmap> bitmapCache;


    private ActivityTransitionLauncher(Activity activity) {
        this.activity = activity;
    }

    public static ActivityTransitionLauncher with(Activity activity) {
        return new ActivityTransitionLauncher(activity);
    }

    public ActivityTransitionLauncher from(View fromView) {
        this.fromView = fromView;
        return this;
    }

    public ActivityTransitionLauncher image(final Bitmap bitmap) {
        new File(activity.getFilesDir().getAbsolutePath() + "/activity_transition/").mkdirs();
        final File imageFile = new File(activity.getFilesDir().getAbsolutePath() + "/activity_transition/", ActivityTransitionLauncher.TEMP_IMAGE_FILE_NAME);
        this.imageFilePath = imageFile.getAbsolutePath();

        BufferedOutputStream bos = null;
        try {
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
            bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        } catch (FileNotFoundException e) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "file not found", e);
            }
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "can't create file", e);
            }
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    //IOException, NullPointerException
                    Log.i(TAG, "fail save image", e);
                }
            }
        }

        bitmapCache = new WeakReference<Bitmap>(bitmap);
        return this;
    }

    public void launch(Intent intent) {
        int[] screenLocation = new int[2];
        fromView.getLocationOnScreen(screenLocation);
        intent.
                putExtra(EXTRA_IMAGE_LEFT, screenLocation[0]).
                putExtra(EXTRA_IMAGE_TOP, screenLocation[1]).
                putExtra(EXTRA_IMAGE_WIDTH, fromView.getMeasuredWidth()).
                putExtra(EXTRA_IMAGE_HEIGHT, fromView.getMeasuredHeight());
        if (imageFilePath != null) {
            intent.putExtra(EXTRA_IMAGE_PATH, imageFilePath);
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }
}
