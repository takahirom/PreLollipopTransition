package com.kogitune.prelollipoptransition;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.kogitune.activity_transition.core.TransitionAnimation;
import com.squareup.spoon.Spoon;

import junit.framework.Assert;

import org.fest.assertions.api.ANDROID;

/**
 * Created by takahirom on 15/10/03.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Instrumentation instrumentation;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
    }

    public void testShouldOpen() {
        Spoon.screenshot(getActivity(), "on_main_activity_open_start");
        Assert.assertTrue(true);
    }

    public void testFragmentTransition() throws InterruptedException {

        Spoon.screenshot(getActivity(), "init");
        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.support_fragment_start_imageview);
        final ImageView imageView2 = (ImageView) getActivity().findViewById(R.id.fragment_start_imageview);
        instrumentation.waitForIdleSync();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertTrue(imageView.performClick());
                assertTrue(imageView2.performClick());
            }
        });
        // Wait for animation
        Thread.sleep(2000l);

        instrumentation.waitForIdleSync();
        Spoon.screenshot(getActivity(), "support_fragment_transition");
        sendKeys(KeyEvent.KEYCODE_BACK);
        instrumentation.waitForIdleSync();
        Thread.sleep(2000l);


        Spoon.screenshot(getActivity(), "support_fragment_transition_backpress1");

        sendKeys(KeyEvent.KEYCODE_BACK);
        instrumentation.waitForIdleSync();
        Thread.sleep(3000l);

        Spoon.screenshot(getActivity(), "support_fragment_transition_backpress2");
    }


    public void testGoSubActivity() throws InterruptedException {
        Spoon.screenshot(getActivity(), "init");
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(SubActivity.class.getName(), null, false);

        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView);
        instrumentation.waitForIdleSync();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertTrue(imageView.performClick());
            }
        });


        Activity activity = instrumentation.waitForMonitor(monitor);

        // Verify new activity was shown.
        ANDROID.assertThat(monitor).hasHits(1);
        // Wait for animation
        Thread.sleep(2000l);
        Spoon.screenshot(activity, "sub_activity_shown");

        // subactivity -> mainactivity
        sendKeys(KeyEvent.KEYCODE_BACK);
        // Wait for animation
        Thread.sleep(2000l);

        instrumentation.waitForIdleSync();
        Spoon.screenshot(getActivity(), "main_activity_backed");
    }

    public void testGoSubActivity2() throws InterruptedException {
        Spoon.screenshot(getActivity(), "init");
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(SubActivity2.class.getName(), null, false);

        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView2);
        instrumentation.waitForIdleSync();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertTrue(imageView.performClick());
            }
        });


        Activity activity = instrumentation.waitForMonitor(monitor);

        // Verify new activity was shown.
        ANDROID.assertThat(monitor).hasHits(1);
        // Wait for animation
        Thread.sleep(2000l);
        Spoon.screenshot(activity, "sub_activity_shown");


        // subactivity -> mainactivity
        sendKeys(KeyEvent.KEYCODE_BACK);
        // Wait for animation
        Thread.sleep(2000l);

        instrumentation.waitForIdleSync();
        Spoon.screenshot(getActivity(), "main_activity_backed");
    }

    public void testClearCacheGoSubActivity2() throws InterruptedException {
        Spoon.screenshot(getActivity(), "init");
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(SubActivity2.class.getName(), null, false);

        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView2);
        instrumentation.waitForIdleSync();

        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertTrue(imageView.performClick());
            }
        });
        instrumentation.runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        // clear bitmapCache
                        TransitionAnimation.bitmapCache.clear();
                    }
                });


        Activity activity = instrumentation.waitForMonitor(monitor);

        // Verify new activity was shown.
        ANDROID.assertThat(monitor).hasHits(1);
        // Wait for animation
        Thread.sleep(2000l);
        Spoon.screenshot(activity, "sub_activity_shown");


        // subactivity -> mainactivity
        sendKeys(KeyEvent.KEYCODE_BACK);
        // Wait for animation
        Thread.sleep(2000l);

        instrumentation.waitForIdleSync();
        Spoon.screenshot(getActivity(), "main_activity_backed");
    }
}