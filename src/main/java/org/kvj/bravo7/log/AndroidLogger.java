package org.kvj.bravo7.log;

import android.util.Log;

/**
 * Created by kvorobyev on 6/16/14.
 */
public class AndroidLogger  implements Logger.LoggerOutput {

    private String prefix = "";

    public AndroidLogger() {
    }

    public AndroidLogger(String prefix) {
        this();
        this.prefix = prefix;
    }

    @Override
    public boolean output(Logger logger, Logger.LoggerLevel level, Throwable e, String line) {
        switch (level) {
            case Debug:
                Log.d(prefix+logger.getTitle(), line);
                break;
            case Info:
                Log.i(prefix+logger.getTitle(), line);
                break;
            case Warning:
                Log.w(prefix+logger.getTitle(), line);
                break;
            case Error:
                Log.e(prefix+logger.getTitle(), line, e);
                break;
        }
        return true;
    }
}
