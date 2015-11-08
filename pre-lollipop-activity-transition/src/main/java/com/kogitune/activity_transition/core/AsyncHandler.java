package com.kogitune.activity_transition.core;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Helper class for managing the background thread used to perform io operations
 * and handle async broadcasts.
 */
public final class AsyncHandler {
    private static final HandlerThread sHandlerThread = new HandlerThread("AsyncHandler");
    private static final Handler sHandler;

    static {
        sHandlerThread.start();
        sHandler = new Handler(sHandlerThread.getLooper());
    }

    private AsyncHandler() {
    }

    public static void post(Runnable r) {
        sHandler.post(r);
    }
}