package com.kogitune.activity_transition;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by takam on 2015/03/26.
 */
public class ActivityTransitionLauncher {
    private final Activity activity;
    private View fromView;

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

    public void launch(Intent intent) {
        int[] screenLocation = new int[2];
        fromView.getLocationOnScreen(screenLocation);
        intent.
                putExtra(BuildConfig.APPLICATION_ID + ".left", screenLocation[0]).
                putExtra(BuildConfig.APPLICATION_ID + ".top", screenLocation[1]).
                putExtra(BuildConfig.APPLICATION_ID + ".width", fromView.getMeasuredWidth()).
                putExtra(BuildConfig.APPLICATION_ID + ".height", fromView.getMeasuredHeight());
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }
}
