package com.kogitune.activity_transition;

import android.app.Activity;
import android.view.View;

import com.kogitune.activity_transition.core.MoveData;

/**
 * Created by takam on 2015/03/30.
 */
public class ExitActivityTransition {
    private final MoveData moveData;


    public ExitActivityTransition(MoveData moveData) {
        this.moveData = moveData;
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
