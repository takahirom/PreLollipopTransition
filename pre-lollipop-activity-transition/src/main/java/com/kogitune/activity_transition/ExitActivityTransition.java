package com.kogitune.activity_transition;

import android.app.Activity;
import android.view.View;

/**
 * Created by takam on 2015/03/30.
 */
public class ExitActivityTransition {
    private ActivityTransition activityTransition;


    public ExitActivityTransition(ActivityTransition activityTransition) {
        this.activityTransition = activityTransition;
    }

    public void exit(final Activity activity){
        runExitAnimation(new Runnable() {
            @Override
            public void run() {
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        });
    }

    private void runExitAnimation(final Runnable endAction) {
        View view = activityTransition.toView;
        int duration = activityTransition.duration;
        int leftDelta = activityTransition.leftDelta;
        int topDelta = activityTransition.topDelta;
        float widthScale = activityTransition.widthScale;
        float heightScale = activityTransition.heightScale;
        view.animate().setDuration(duration).
                scaleX(widthScale).scaleY(heightScale).
                translationX(leftDelta).translationY(topDelta);
        view.postDelayed(endAction, duration);
    }
}
