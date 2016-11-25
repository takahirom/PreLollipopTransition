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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;
import com.kogitune.prelollipoptransition.fragment.EndFragment;
import com.kogitune.prelollipoptransition.fragment.SubFragment;
import com.kogitune.prelollipoptransition.support_fragment.SupportStartFragment;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, SubActivity.class);
                ActivityTransitionLauncher
                        .with(MainActivity.this)
                        .from(v)
                        .launch(intent);
            }
        });

        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, SubActivity2.class);
                // set bitmap for animation
                // use startActivityForResult
                Bundle transitionBundle = ActivityTransitionLauncher
                        .with(MainActivity.this)
                        .image(BitmapFactory.decodeResource(getResources(), R.drawable.photo))
                        .from(v)
                        .createBundle();
                intent.putExtras(transitionBundle);
                startActivityForResult(intent, REQUEST_CODE);
                // you should prevent default activity tansition animation
                overridePendingTransition(0, 0);
            }
        });
        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Fragment toFragment = new SubFragment();
                //init your bundle first!!
                Bundle bundle = new Bundle();
                bundle.putString("Test", "Test");
                toFragment.setArguments(bundle);

                //You should call this method after init your argumentsBundle.
                FragmentTransitionLauncher
                        .with(v.getContext())
                        .from(v)
                        .prepare(toFragment);
                getSupportFragmentManager().beginTransaction().replace(R.id.parent_container, toFragment).addToBackStack(null).commit();
            }
        });

        findViewById(R.id.listViewExampleButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                Bundle transitionBundle = ActivityTransitionLauncher
                        .with(MainActivity.this)
//                        .image(BitmapFactory.decodeResource(getResources(), R.drawable.photo))
                        .from(v)
                        .createBundle();
                intent.putExtras(transitionBundle);
                startActivity(intent);
                // you should prevent default activity tansition animation
                overridePendingTransition(0, 0);
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
