package org.kvj.bravo7.util;

import android.os.Build;

/**
 * Created by kvorobyev on 4/9/15.
 */
public class Compat {

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
}
