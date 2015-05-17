package com.kogitune.activity_transition.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.kogitune.activity_transition.core.TransitionBundleFactory;

/**
 * Created by takam on 2015/03/26.
 */
public class FragmentTransitionLauncher {
    private static final String TAG = "TransitionLauncher";

    private final Context context;
    private View fromView;
    private Bitmap bitmap;


    private FragmentTransitionLauncher(Context context) {
        this.context = context;
    }

    public static FragmentTransitionLauncher with(Context context) {
        return new FragmentTransitionLauncher(context);
    }

    public FragmentTransitionLauncher from(View fromView) {
        this.fromView = fromView;
        return this;
    }

    public FragmentTransitionLauncher image(final Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }

    public void prepare(Fragment toFragment) {
        final Bundle transitionBundle = TransitionBundleFactory.createTransitionBundle(context, fromView, bitmap);
        toFragment.setArguments(transitionBundle);
    }

    public void prepare(android.support.v4.app.Fragment toFragment) {
        final Bundle transitionBundle = TransitionBundleFactory.createTransitionBundle(context, fromView, bitmap);
        toFragment.setArguments(transitionBundle);
    }
}
