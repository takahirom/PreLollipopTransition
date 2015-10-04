package com.kogitune.prelollipoptransition;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.ListView;

import com.squareup.spoon.Spoon;

/**
 * Created by takahirom on 15/10/03.
 */
public class ListViewActivityTest extends ActivityInstrumentationTestCase2<ListViewActivity> {

    private Instrumentation instrumentation;

    public ListViewActivityTest() {
        super(ListViewActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
    }

    public void testListClick() throws InterruptedException {
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(SubActivity.class.getName(), null, false);
        Spoon.screenshot(getActivity(), "init");

        // listviewactivity -> subactivity
        final ListView listView = (ListView) getActivity().findViewById(R.id.list);
        instrumentation.waitForIdleSync();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertTrue(listView.performItemClick(listView.getChildAt(0), 0, 0));
            }
        });
        Activity detailActivity = instrumentation.waitForMonitor(monitor);
        // Wait for animation
        Thread.sleep(2000l);

        instrumentation.waitForIdleSync();
        Spoon.screenshot(detailActivity, "listview_transition");

        // subactivity -> listviewactivity
        sendKeys(KeyEvent.KEYCODE_BACK);
        // Wait for animation
        Thread.sleep(2000l);

        instrumentation.waitForIdleSync();
        Spoon.screenshot(getActivity(), "listview_transition_backpress");
    }


}