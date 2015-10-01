package com.kogitune.prelollipoptransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;


public class SubActivity2 extends AppCompatActivity {

    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        exitTransition = ActivityTransition
                .with(getIntent())
                .to(findViewById(R.id.sub_imageView))
                .interpolator(new BounceInterpolator())
                .start(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        exitTransition.interpolator(new OvershootInterpolator()).exit(this);
    }
}
