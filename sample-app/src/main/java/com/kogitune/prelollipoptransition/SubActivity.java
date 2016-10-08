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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.kogitune.activity_transition.SimpleAnimatorListener;


public class SubActivity extends AppCompatActivity {

    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        exitTransition = ActivityTransition
                .with(getIntent())
                .enterListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.e("TAG", "onEnterAnimationEnd");
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.e("TAG", "onOEnterAnimationStart ");
                    }
                })
                .to(findViewById(R.id.sub_imageView))
                .start(savedInstanceState)
                .exitListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.e("TAG", "onOutAnimationStart: ");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.e("TAG", "onOutAnimationEnd: ");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }
}
