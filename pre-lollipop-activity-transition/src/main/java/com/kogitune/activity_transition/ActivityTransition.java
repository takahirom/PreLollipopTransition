package com.kogitune.activity_transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.Transition;

/**
 * Created by takam on 2015/03/26.
 */
public class ActivityTransition {
    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private Intent fromIntent;
    int duration = 1000;
    View toView;

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

    public ActivityTransition duration(int duration){
        this.duration = duration;
        return this;
    }


    public ExitActivityTransition start(Bundle savedInstanceState) {
        final Context context = toView.getContext();
        final Bundle bundle = fromIntent.getExtras();
        final MoveData moveData = Transition.startAnimation(context, toView, bundle, savedInstanceState, duration, sDecelerator);
        return new ExitActivityTransition(moveData);
    }

}
