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

package com.kogitune.activity_transition.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.kogitune.activity_transition.core.TransitionBundleFactory;

public class FragmentTransitionLauncher {
    private static final String TAG = "TransitionLauncher";
    static final String TRANSITION_BUNDLE = "TransitionBundle";


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
    /**
     * You should call this method after init your argumentsBundle.otherwise the transitionBundle will be not work.
     */
    public void prepare(Fragment toFragment) {
        final Bundle transitionBundle = TransitionBundleFactory.createTransitionBundle(context, fromView, bitmap);
        Bundle arguments = toFragment.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putBundle(TRANSITION_BUNDLE, transitionBundle);
        toFragment.setArguments(arguments);
    }
    /**
     * You should call this method after init your argumentsBundle.otherwise the transitionBundle will be not work.
     */
    public void prepare(android.support.v4.app.Fragment toFragment) {
        final Bundle transitionBundle = TransitionBundleFactory.createTransitionBundle(context, fromView, bitmap);
        Bundle arguments = toFragment.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putBundle(TRANSITION_BUNDLE, transitionBundle);
        toFragment.setArguments(arguments);
    }
}
