package com.kogitune.prelollipoptransition;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;


public class SubActivity2 extends ActionBarActivity {

    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.sub_imageView)).start(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }
}
