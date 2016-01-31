/*
 * Copyright (C) 2015 takahirom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.kogitune.activitytransition;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;

import com.kogitune.activitytransition.core.MoveData;
import com.kogitune.activitytransition.core.TransitionAnimation;

public class ActivityTransition {
    private int duration = 1000;
    private View toView;
    private TimeInterpolator interpolator;
    private Intent fromIntent;
    private String toViewName;

    private ActivityTransition(Intent intent) {
        this.fromIntent = intent;
    }

    public static ActivityTransition with(Intent intent) {
        return new ActivityTransition(intent);
    }

    public ActivityTransition to(View toView, String name) {
        this.toView = toView;
        this.toViewName = name;
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

        if (Build.VERSION.SDK_INT >= 21) {
            ViewCompat.setTransitionName(toView, toViewName);
            final Window window = ((Activity) toView.getContext()).getWindow();
            TransitionSet set = new TransitionSet();
            set.addTransition(new ChangeBounds());
            set.addTransition(new ChangeImageTransform());
            set.setInterpolator(interpolator);
            window.setSharedElementEnterTransition(set);
            window.setSharedElementExitTransition(set);
            return new ExitActivityTransition(null);
        }
        final Context context = toView.getContext();
        final Bundle bundle = fromIntent.getExtras();
        final MoveData moveData = TransitionAnimation.startAnimation(context, toView, bundle, savedInstanceState, duration, interpolator);

        return new ExitActivityTransition(moveData);
    }

}
