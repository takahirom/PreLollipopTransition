package com.kogitune.prelollipoptransition.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.Transition;
import com.kogitune.prelollipoptransition.R;

/**
 * Created by takam on 2015/05/16.
 */
public class EndFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end, container, false);
        final MoveData moveData = Transition.startAnimation(getActivity(), v.findViewById(R.id.fragment_imageView), getArguments(), savedInstanceState, 1000, new LinearInterpolator());
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.getAction() != KeyEvent.ACTION_UP) {
                        return true;
                    }
                    Transition.startExitAnimation(moveData, new Runnable() {
                        @Override
                        public void run() {
                            getFragmentManager().popBackStack();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        return v;
    }

}
