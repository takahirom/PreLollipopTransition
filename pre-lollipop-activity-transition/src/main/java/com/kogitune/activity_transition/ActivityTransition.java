package com.kogitune.activity_transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

/**
 * Created by takam on 2015/03/26.
 */
public class ActivityTransition {
    private int duration = 1000;
    private View toView;
    private TimeInterpolator interpolator;
    private Intent fromIntent;

    private ActivityTransition(Intent intent) {
        this.fromIntent = intent;
    }

    public static ActivityTransition with(Intent intent) {
        return new ActivityTransition(intent);
    }

    public ActivityTransition to(View toView) {
        this.toView = toView;
        return this;
    }

    public ActivityTransition duration(int duration) {
        this.duration = duration;
        return this;
    }

    public ActivityTransition interpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public ExitActivityTransition start(Bundle savedInstanceState) {
        if (interpolator == null) {
            interpolator = new DecelerateInterpolator();
        }
        final Context context = toView.getContext();
        final Bundle bundle = fromIntent.getExtras();
        final MoveData moveData = TransitionAnimation.startAnimation(context, toView, bundle, savedInstanceState, duration, interpolator);
        return new ExitActivityTransition(moveData);
    }

}
