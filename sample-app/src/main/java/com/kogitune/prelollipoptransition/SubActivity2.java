package com.kogitune.prelollipoptransition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;


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
                .start(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, "ok");
        setResult(RESULT_OK, intent);
        exitTransition.interpolator(new OvershootInterpolator()).exit(this);
    }
}
