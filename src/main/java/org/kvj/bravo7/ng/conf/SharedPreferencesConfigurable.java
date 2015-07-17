package org.kvj.bravo7.ng.conf;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import org.kvj.bravo7.util.Compat;

/**
 * Created by vorobyev on 7/17/15.
 */
public class SharedPreferencesConfigurable implements Configurable {

    private final SharedPreferences preferences;
    private final Context context;

    public SharedPreferencesConfigurable(Context context, SharedPreferences preferences) {
        this.preferences = preferences;
        this.context = context;
    }

    @Override
    public String getString(int name, String def) {
        return preferences.getString(context.getString(name), def);
    }

    @Override
    public void setString(int name, String value) {
        commit(editor().putString(context.getString(name), value));
    }

    @Override
    public boolean getBool(int name, boolean def) {
        return preferences.getBoolean(context.getString(name), def);
    }

    @Override
    public void setBool(int name, boolean value) {
        commit(editor().putBoolean(context.getString(name), value));
    }

    @Override
    public int getInt(int name, int def) {
        return preferences.getInt(context.getString(name), def);
    }

    @Override
    public void setInt(int name, int value) {
        commit(editor().putInt(context.getString(name), value));
    }

    private SharedPreferences.Editor editor() {
        return preferences.edit();
    }

    private void commit(final SharedPreferences.Editor editor) {
        Compat.levelAware(9, new Runnable() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void run() {
                editor.apply();
            }
        }, new Runnable() {
            @Override
            public void run() {
                editor.commit();
            }
        });
    }
}
