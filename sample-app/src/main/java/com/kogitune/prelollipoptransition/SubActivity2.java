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

package com.kogitune.prelollipoptransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

import static android.app.Activity.RESULT_OK;


public class SubActivity2 extends AppCompatActivity {

    public static final String EXTRA_RESULT = "result";
    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        Intent intent = getIntent();
        exitTransition = ActivityTransition
                .with(intent)
                .to(findViewById(R.id.sub_imageView))
                .interpolator(new BounceInterpolator())
                .enterListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.d("TAG", "onEnterAnimationStart!! ");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.d("TAG", "onEnterAnimationEnd!!");
                    }

                })
                .start(savedInstanceState);
        exitTransition.exitListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("TAG", "onExitAnimationEnd!!");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("TAG", "onExitAnimationStart!!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, "ok");
        setResult(RESULT_OK, intent);
        exitTransition.interpolator(new OvershootInterpolator()).exit(this);
    }
}
