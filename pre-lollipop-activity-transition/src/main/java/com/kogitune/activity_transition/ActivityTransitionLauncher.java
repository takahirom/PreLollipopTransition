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
    public static final String EXTRA_IMAGE_LEFT = ".left";
    public static final String EXTRA_IMAGE_TOP = ".top";
    public static final String EXTRA_IMAGE_WIDTH = ".width";
    public static final String EXTRA_IMAGE_HEIGHT = ".height";
    public static final String EXTRA_IMAGE_PATH = ".imageFilePath";

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
        final Boolean isDebug = (Boolean) BuildConfigUtils.getBuildConfigValue(activity, "DEBUG");

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
        return this;
    }

    public void launch(Intent intent) {
        int[] screenLocation = new int[2];
        fromView.getLocationOnScreen(screenLocation);
        final String appId = (String) BuildConfigUtils.getBuildConfigValue(activity, "APPLICATION_ID");
        intent.
                putExtra(appId + EXTRA_IMAGE_LEFT, screenLocation[0]).
                putExtra(appId + EXTRA_IMAGE_TOP, screenLocation[1]).
                putExtra(appId + EXTRA_IMAGE_WIDTH, fromView.getMeasuredWidth()).
                putExtra(appId + EXTRA_IMAGE_HEIGHT, fromView.getMeasuredHeight());
        if (imageFilePath != null) {
            intent.putExtra(appId + EXTRA_IMAGE_PATH, imageFilePath);
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }
}
