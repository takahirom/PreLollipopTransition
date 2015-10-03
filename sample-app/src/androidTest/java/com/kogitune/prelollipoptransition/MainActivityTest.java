package com.kogitune.prelollipoptransition;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.kogitune.prelollipoptransition.rule.EspressoTestRule;
import com.squareup.spoon.Spoon;

import junit.framework.Assert;

import org.fest.assertions.api.ANDROID;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by takahirom on 15/10/03.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    @Rule
    public EspressoTestRule activityTestRule = new EspressoTestRule<>(MainActivity.class);
    private Instrumentation instrumentation;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    @Before
    public void setUp() throws Exception {
        EspressoTestRule.disableAllAnimations();
        super.setUp();
        instrumentation = getInstrumentation();
    }

    @Test
    public void testShouldOpen() {
        Spoon.screenshot(getActivity(), "on_main_activity_open_start");
        Assert.assertTrue(true);
    }

    @Test
    public void testShouldGoSubActivity() throws InterruptedException {
        final Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(SubActivity.class.getName(), null, false);

        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView);
        instrumentation.waitForIdleSync();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertTrue(imageView.performClick());
            }
        });


        Activity activity = instrumentation.waitForMonitor(monitor);
        ANDROID.assertThat(monitor).hasHits(1);
        // Verify new activity was shown.
        Spoon.screenshot(activity, "sub_activity_shown");
    }

    @Test
    public void testShouldGoSubActivity2() throws InterruptedException {
        final Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(SubActivity.class.getName(), null, false);

        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView);
        instrumentation.waitForIdleSync();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertTrue(imageView.performClick());
            }
        });


        Activity activity = instrumentation.waitForMonitor(monitor);
        ANDROID.assertThat(monitor).hasHits(1);
        // Verify new activity was shown.
        Spoon.screenshot(activity, "sub_activity_shown");
    }
}