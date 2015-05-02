package com.kogitune.activity_transition;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by takam on 2015/05/01.
 */
public class BuildConfigUtils {
    public static Object getBuildConfigValue(Context context, String fieldName) {
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
