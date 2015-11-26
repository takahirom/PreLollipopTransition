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

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.ListView;

import com.squareup.spoon.Spoon;

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