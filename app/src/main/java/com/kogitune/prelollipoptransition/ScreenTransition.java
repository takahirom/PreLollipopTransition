package com.kogitune.prelollipoptransition;

import android.app.Activity;
import android.view.View;

/**
 * Created by takam on 2015/03/26.
 */
public class ScreenTransition {
    private final Activity activity;
    private View toView;

    public ScreenTransition(Activity activity) {
        this.activity = activity;
    }

    public static ScreenTransition with(Activity activity) {
        return new ScreenTransition(activity);
    }

    public ScreenTransition to(View toView) {
        this.toView = toView;
        return this;
    }
}
