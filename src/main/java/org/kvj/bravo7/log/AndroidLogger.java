package org.kvj.bravo7.log;

import android.util.Log;

/**
 * Created by kvorobyev on 6/16/14.
 */
public class AndroidLogger  implements Logger.LoggerOutput {
    @Override
    public boolean output(Logger logger, Logger.LoggerLevel level, Throwable e, String line) {
        switch (level) {

            case Debug:
                Log.d(logger.getTitle(), line);
                break;
            case Info:
                Log.i(logger.getTitle(), line);
                break;
            case Warning:
                Log.w(logger.getTitle(), line);
                break;
            case Error:
                Log.e(logger.getTitle(), line, e);
                break;
        }
        return true;
    }
}
