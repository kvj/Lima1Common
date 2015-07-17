package org.kvj.bravo7.ng.conf;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by vorobyev on 7/17/15.
 */
public class BundleConfigurable implements Configurable {

    private final Bundle bundle;
    private final Context context;

    public BundleConfigurable(Context context, Bundle bundle) {
        this.bundle = bundle;
        this.context = context;
    }

    @Override
    public String getString(int name, String def) {
        String value = bundle.getString(context.getString(name));
        if (null == value) {
            return def;
        }
        return value;
    }

    @Override
    public void setString(int name, String value) {
        bundle.putString(context.getString(name), value);
    }

    @Override
    public boolean getBool(int name, boolean def) {
        return bundle.getBoolean(context.getString(name), def);
    }

    @Override
    public void setBool(int name, boolean value) {
        bundle.putBoolean(context.getString(name), value);
    }

    @Override
    public int getInt(int name, int def) {
        return bundle.getInt(context.getString(name), def);
    }

    @Override
    public void setInt(int name, int value) {
        bundle.putInt(context.getString(name), value);
    }

    public Bundle bundle() {
        return bundle;
    }
}
