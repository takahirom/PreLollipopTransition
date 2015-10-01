package com.kogitune.activity_transition;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.view.animation.DecelerateInterpolator;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

/**
 * Created by takam on 2015/03/30.
 */
public class ExitActivityTransition {
    private final MoveData moveData;
    private TimeInterpolator interpolator;


    public ExitActivityTransition(MoveData moveData) {
        this.moveData = moveData;
    }

    public ExitActivityTransition interpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public void exit(final Activity activity) {
        if (interpolator == null) {
            interpolator = new DecelerateInterpolator();
        }
        TransitionAnimation.startExitAnimation(moveData, interpolator, new Runnable() {
            @Override
            public void run() {
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        });
    }

}
