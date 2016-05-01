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

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.kogitune.activitytransition.ActivityTransitionLauncher;
import com.kogitune.prelollipoptransition.supportfragment.SupportStartFragment;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, SubActivity.class);
                ActivityTransitionLauncher
                        .with(MainActivity.this)
                        .from(v, "image")
                        .launch(intent);
            }
        });

        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, SubActivity2.class);
                // set bitmap for animation
                // use startActivityForResult
                final ActivityTransitionLauncher activityTransitionLauncher = ActivityTransitionLauncher
                        .with(MainActivity.this)
                        .image(BitmapFactory.decodeResource(getResources(), R.drawable.photo))
                        .from(v, "image");
                Bundle transitionBundle = activityTransitionLauncher.createBundle();
                intent.putExtras(transitionBundle);
                ActivityCompat.startActivityForResult(MainActivity.this, intent, REQUEST_CODE, activityTransitionLauncher.createOptions());
                // you should prevent default activity transition animation
                overridePendingTransition(0, 0);
            }
        });

        findViewById(R.id.listViewExampleButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.support_content, new SupportStartFragment())
                    .commit();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, new com.kogitune.prelollipoptransition.fragment.StartFragment())
                    .commit();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode) {
            String resultExtra = data.getStringExtra(SubActivity2.EXTRA_RESULT);
            Toast.makeText(this, "onActivityResult:" + resultExtra, Toast.LENGTH_SHORT).show();
        }
    }
}
