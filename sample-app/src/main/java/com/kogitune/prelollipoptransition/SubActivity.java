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

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.kogitune.activitytransition.ActivityTransition;
import com.kogitune.activitytransition.ExitActivityTransition;


public class SubActivity extends AppCompatActivity {

    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.sub_imageView), "image").start(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }
}
