package com.kogitune.prelollipoptransition.rule;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;
import android.view.WindowManager;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
/**
 * Created by takahirom on 15/10/03.
 * Hacking through Espresso issues...
 * from:https://gist.github.com/patrickhammond/19e584b90d7aae20f8f4
 */

/**
 * Make sure this gets added to the manifest of the application under test (typically a manifest in the debug variant).
 * <p/>
 * <code>
 * <uses-permission android:name="android.permission.SET_ANIMATION_SCALE" />
 * <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
 * <uses-permission android:name="android.permission.WAKE_LOCK"/>
 * </code>
 *
 * @param <T>
 */
public class EspressoTestRule<T extends Activity> extends ActivityTestRule<T> {
    private static final String TAG = EspressoTestRule.class.getSimpleName();

    private static final String ANIMATION_PERMISSION = "android.permission.SET_ANIMATION_SCALE";
    private static final float ANIMATION_DISABLED = 0.0f;
    private static final float ANIMATION_DEFAULT = 1.0f;

    public EspressoTestRule(Class<T> activityClass) {
        super(activityClass);
    }

    public EspressoTestRule(Class<T> activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
    }

    public EspressoTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    public static void disableAllAnimations() {
        if (getAnimationPermissionStatus() == PackageManager.PERMISSION_GRANTED) {
            setSystemAnimationsScale(ANIMATION_DISABLED);
        } else {
            Log.w(TAG, "Not granted permission to change animation scale.");
        }
    }

    private static int getAnimationPermissionStatus() {
        Context context = InstrumentationRegistry.getTargetContext();
        return context.checkCallingOrSelfPermission(ANIMATION_PERMISSION);
    }

    // https://code.google.com/p/android-test-kit/wiki/DisablingAnimations
    private static void setSystemAnimationsScale(float animationScale) {
        try {
            Class<?> windowManagerStubClazz = Class.forName("android.view.IWindowManager$Stub");
            Method asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder.class);
            Class<?> serviceManagerClazz = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClazz.getDeclaredMethod("getService", String.class);
            Class<?> windowManagerClazz = Class.forName("android.view.IWindowManager");
            Method setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales", float[].class);
            Method getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales");

            IBinder windowManagerBinder = (IBinder) getService.invoke(null, "window");
            Object windowManagerObj = asInterface.invoke(null, windowManagerBinder);
            float[] currentScales = (float[]) getAnimationScales.invoke(windowManagerObj);
            for (int i = 0; i < currentScales.length; i++) {
                currentScales[i] = animationScale;
            }
            setAnimationScales.invoke(windowManagerObj, new Object[]{currentScales});
        } catch (Exception ex) {
            Log.e(TAG, "Could not use reflection to change animation scale to: " + animationScale, ex);
        }
    }

    public static Set<Activity> getActivitiesInStages(Stage... stages) {
        final Set<Activity> activities = new HashSet<>();
        final ActivityLifecycleMonitor instance = ActivityLifecycleMonitorRegistry.getInstance();
        for (Stage stage : stages) {
            final Collection<Activity> activitiesInStage = instance.getActivitiesInStage(stage);
            if (activitiesInStage != null) {
                activities.addAll(activitiesInStage);
            }
        }
        return activities;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        try {
            disableAllAnimations();
            return super.apply(base, description);
        } finally {
            enableAllAnimations();
            closeAllActivities();
        }
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        ui(new UIRunner() {
            @Override
            public void run() {
                riseAndShine(getActivity());
            }
        });
    }

    public void ui(final UIRunner runner) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    runner.run();
                }
            });
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    // Inspired from https://gist.github.com/JakeWharton/f50f3b4d87e57d8e96e9
    @SuppressWarnings("deprecation")
    private void riseAndShine(Activity activity) {
        KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock keyguardLock = keyguardManager.newKeyguardLock(activity.getLocalClassName());
        keyguardLock.disableKeyguard();

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        WakeLock lock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE,
                "wakeup!");

        lock.acquire();
        lock.release();
    }

    private void enableAllAnimations() {
        if (getAnimationPermissionStatus() == PackageManager.PERMISSION_GRANTED) {
            setSystemAnimationsScale(ANIMATION_DEFAULT);
        } else {
            Log.w(TAG, "Not granted permission to change animation scale.");
        }
    }

    // See https://code.google.com/p/android-test-kit/issues/detail?id=66
    private void closeAllActivities() {
        try {
            Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
            closeAllActivities(instrumentation);
        } catch (Exception ex) {
            Log.e(TAG, "Could not use close all activities", ex);
        }
    }

    private void closeAllActivities(Instrumentation instrumentation) throws Exception {
        final int NUMBER_OF_RETRIES = 100;
        int i = 0;
        while (closeActivity(instrumentation)) {
            if (i++ > NUMBER_OF_RETRIES) {
                throw new AssertionError("Limit of retries excesses");
            }
            Thread.sleep(200);
        }
    }

    private boolean closeActivity(Instrumentation instrumentation) throws Exception {
        final Boolean activityClosed = callOnMainSync(instrumentation, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Set<Activity> activities = getActivitiesInStages(Stage.RESUMED,
                        Stage.STARTED, Stage.PAUSED, Stage.STOPPED, Stage.CREATED);
                activities.removeAll(getActivitiesInStages(Stage.DESTROYED));
                if (activities.size() > 0) {
                    final Activity activity = activities.iterator().next();
                    activity.finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        if (activityClosed) {
            instrumentation.waitForIdleSync();
        }
        return activityClosed;
    }

    private <X> X callOnMainSync(Instrumentation instrumentation, final Callable<X> callable) throws Exception {
        final AtomicReference<X> retAtomic = new AtomicReference<>();
        final AtomicReference<Throwable> exceptionAtomic = new AtomicReference<>();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    retAtomic.set(callable.call());
                } catch (Throwable e) {
                    exceptionAtomic.set(e);
                }
            }
        });
        final Throwable exception = exceptionAtomic.get();
        if (exception != null) {
            throw new RuntimeException(exception);
        }
        return retAtomic.get();
    }

    public interface UIRunner {
        void run();
    }
}