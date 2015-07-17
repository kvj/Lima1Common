package org.kvj.bravo7.util;

import android.os.Build;

/**
 * Created by kvorobyev on 4/9/15.
 */
public class Compat {

    public interface Producer<T> {
        public T produce();
    }

    public static void levelAware(int level, Runnable after, Runnable before) {
        if(Build.VERSION.SDK_INT >= level) {
            if (null != after) after.run();
        } else {
            if (null != before) before.run();
        }
    }

    public static void levelAware(int level, Runnable after) {
        levelAware(level, after, null);
    }

    public static <T> T produceLevelAware(int level, Producer<T> after, Producer<T> before) {
        T result = null;
        if(Build.VERSION.SDK_INT >= level) {
            if (null != after) result = after.produce();
        } else {
            if (null != before) result = before.produce();
        }
        return result;
    }
}
