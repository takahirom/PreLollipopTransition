package com.kogitune.prelollipoptransition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by takam on 2015/03/26.
 */
public class ScreenTransitionLauncher {
    private final Activity activity;
    private View fromView;

    private ScreenTransitionLauncher(Activity activity) {
        this.activity = activity;
    }

    public static ScreenTransitionLauncher with(Activity activity) {
        return new ScreenTransitionLauncher(activity);
    }

    public ScreenTransitionLauncher from(View fromView) {
        this.fromView = fromView;
        return this;
    }

    private Bitmap createViewBitmap(View v) {
        final Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final int savedCount = canvas.save(Canvas.CLIP_SAVE_FLAG | Canvas.MATRIX_SAVE_FLAG);
        canvas.restoreToCount(savedCount);

        return bitmap;
    }

    public void startActivity(Intent intent) {
        int[] screenLocation = new int[2];
        fromView.getLocationOnScreen(screenLocation);
        int orientation = activity.getResources().getConfiguration().orientation;
        intent.
                putExtra(BuildConfig.APPLICATION_ID + ".orientation", orientation).
                putExtra(BuildConfig.APPLICATION_ID + ".image", createViewBitmap(fromView)).
                putExtra(BuildConfig.APPLICATION_ID + ".left", screenLocation[0]).
                putExtra(BuildConfig.APPLICATION_ID + ".top", screenLocation[1]).
                putExtra(BuildConfig.APPLICATION_ID + ".width", fromView.getWidth()).
                putExtra(BuildConfig.APPLICATION_ID + ".height", fromView.getHeight());
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }
}
