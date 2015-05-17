package com.kogitune.activity_transition.fragment;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

/**
 * Created by takam on 2015/03/30.
 */
public class ExitFragmentTransition {
    private final MoveData moveData;
    private Fragment fragment;
    private android.support.v4.app.Fragment supportFragment;


    public ExitFragmentTransition(Fragment fragment, MoveData moveData) {
        this.fragment = fragment;
        this.moveData = moveData;
    }

    public ExitFragmentTransition(final android.support.v4.app.Fragment fragment, MoveData moveData) {
        this.supportFragment = fragment;
        this.moveData = moveData;
    }

    public void startExitListening() {
        startExitListening(null);
    }

    public void startExitListening(final Runnable popBackStackRunnable) {
        final View toView = moveData.toView;
        toView.setFocusableInTouchMode(true);
        toView.requestFocus();
        toView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.getAction() != KeyEvent.ACTION_UP) {
                        return true;
                    }
                    TransitionAnimation.startExitAnimation(moveData, new Runnable() {
                        @Override
                        public void run() {
                            if (popBackStackRunnable != null) {
                                popBackStackRunnable.run();
                                return;
                            }
                            if (fragment == null) {
                                if (!supportFragment.isResumed()) {
                                    return;
                                }
                                final FragmentManager fragmentManager = supportFragment.getFragmentManager();
                                if (fragmentManager != null) {
                                    fragmentManager.popBackStack();
                                }
                            } else {
                                if (!fragment.isResumed()) {
                                    return;
                                }
                                final android.app.FragmentManager fragmentManager = ((Fragment) fragment).getFragmentManager();
                                if (fragmentManager != null) {
                                    fragmentManager.popBackStack();
                                }
                            }
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }

}
