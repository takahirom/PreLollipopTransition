package com.kogitune.activity_transition.fragment;

import android.animation.TimeInterpolator;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

/**
 * Created by takam on 2015/03/26.
 */
public class FragmentTransition {
    private static TimeInterpolator interpolator;
    private int duration = 1000;
    private View toView;
    private android.support.v4.app.Fragment supportFragment;
    private Fragment fragment;

    private FragmentTransition(Fragment fragment) {
        this.fragment = fragment;
    }

    private FragmentTransition(android.support.v4.app.Fragment fragment) {
        this.supportFragment = fragment;
    }

    public static FragmentTransition with(Fragment fragment) {
        return new FragmentTransition(fragment);
    }

    public static FragmentTransition with(android.support.v4.app.Fragment fragment) {
        return new FragmentTransition(fragment);
    }

    public FragmentTransition to(View toView) {
        this.toView = toView;
        return this;
    }

    public FragmentTransition duration(int duration) {
        this.duration = duration;
        return this;
    }


    public FragmentTransition interpolator(TimeInterpolator interpolator) {
        FragmentTransition.interpolator = interpolator;
        return this;
    }

    public ExitFragmentTransition start(Bundle savedInstanceState) {
        if (interpolator == null) {
            interpolator = new DecelerateInterpolator();
        }
        final Context context = toView.getContext();
        final Bundle bundle;
        if (fragment == null) {
            bundle = supportFragment.getArguments();
        } else {
            bundle = fragment.getArguments();
        }
        final MoveData moveData = TransitionAnimation.startAnimation(context, toView, bundle, savedInstanceState, duration, interpolator);
        if (fragment == null) {
            return new ExitFragmentTransition(supportFragment, moveData);
        }
        return new ExitFragmentTransition(fragment, moveData);
    }

}
