package com.kogitune.prelollipoptransition.support_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;
import com.kogitune.prelollipoptransition.R;

/**
 * Created by takam on 2015/05/16.
 */
public class SupportStartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.support_fragment_start, container, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SupportEndFragment toFragment = new SupportEndFragment();
                FragmentTransitionLauncher.with(view.getContext()).from(view.findViewById(R.id.fragment_start_imageview)).prepare(toFragment);
                getFragmentManager().beginTransaction().replace(R.id.support_content, toFragment).addToBackStack(null).commit();
            }
        });
        return v;
    }
}
