package com.kogitune.prelollipoptransition.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogitune.activity_transition.core.Transition;
import com.kogitune.prelollipoptransition.R;

/**
 * Created by takam on 2015/05/16.
 */
public class StartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create view info bundle by view
                final Bundle transitionBundle = Transition.createTransitionBundle(view.getContext(), view, null);
                // Set bundle to fragment
                final EndFragment fragment = new EndFragment();
                fragment.setArguments(transitionBundle);
                getFragmentManager().beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit();
            }
        });
        return v;
    }
}
