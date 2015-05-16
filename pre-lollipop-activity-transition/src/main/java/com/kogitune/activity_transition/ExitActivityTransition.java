package com.kogitune.activity_transition;

import android.app.Activity;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.Transition;

/**
 * Created by takam on 2015/03/30.
 */
public class ExitActivityTransition {
    private final MoveData moveData;


    public ExitActivityTransition(MoveData moveData) {
        this.moveData = moveData;
    }

    public void exit(final Activity activity) {
        Transition.startExitAnimation(moveData, new Runnable() {
            @Override
            public void run() {
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        });
    }

}
