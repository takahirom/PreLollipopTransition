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

package com.kogitune.prelollipoptransition.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogitune.activitytransition.fragment.FragmentTransitionLauncher;
import com.kogitune.prelollipoptransition.R;

public class StartFragment extends Fragment {

    public static final String TRANSITION_NAME = "fragment_image";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        v.findViewById(R.id.fragment_start_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCompat.setTransitionName(view, TRANSITION_NAME);
                final EndFragment toFragment = new EndFragment();
                FragmentTransitionLauncher
                        .with(view.getContext())
                        .image(BitmapFactory.decodeResource(getResources(), R.drawable.photo))
                        .from(view.findViewById(R.id.fragment_start_imageview))
                        .prepare(toFragment);

                final FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, toFragment)
                        .addToBackStack(null);
                if (Build.VERSION.SDK_INT >= 21) {
                    fragmentTransaction
                            .addSharedElement(view, TRANSITION_NAME);
                }
                fragmentTransaction.commit();
            }
        });
        return v;
    }
}
