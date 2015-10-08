package com.kogitune.activity_transition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.kogitune.activity_transition.core.TransitionBundleFactory;

/**
 * Created by takam on 2015/03/26.
 */
public class ActivityTransitionLauncher {
    private static final String TAG = "TransitionLauncher";

    private final Activity activity;
    private View fromView;
    private Bitmap bitmap;


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
        this.bitmap = bitmap;
        return this;
    }

    public Bundle createBundle() {
        return TransitionBundleFactory.createTransitionBundle(activity, fromView, bitmap);
    }

    public void launch(Intent intent) {
        intent.putExtras(createBundle());
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }
}
