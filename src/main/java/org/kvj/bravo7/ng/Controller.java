package org.kvj.bravo7.ng;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.kvj.bravo7.log.AndroidLogger;
import org.kvj.bravo7.log.Logger;
import org.kvj.bravo7.ng.conf.Configurator;
import org.kvj.bravo7.ng.conf.SharedPreferencesConfigurable;

/**
 * Created by kvorobyev on 4/8/15.
 */

public class Controller {

    protected final Context context;
    protected Logger logger = Logger.forInstance(this);

    public Controller(Context context, String name) {
        Logger.setOutput(new AndroidLogger(name));
        this.context = context;
    }

    public Configurator settings() {
        return new Configurator(new SharedPreferencesConfigurable(context, PreferenceManager.getDefaultSharedPreferences(context)));
    }

    public SharedPreferences preferences(String s) {
        return context.getSharedPreferences(s, Context.MODE_PRIVATE);
    }

    public Configurator settings(String s) {
        return new Configurator(new SharedPreferencesConfigurable(context, preferences(s)));
    }

    public Context context() {
        return context;
    }

    public void messageShort(String message) {
        logger.w("Toast:", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void messageLong(String message) {
        logger.w("Toast:", message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
