package org.kvj.bravo7.ng;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kvj.bravo7.log.AndroidLogger;
import org.kvj.bravo7.log.Logger;

import java.util.ArrayList;
import java.util.List;

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

    public SharedPreferences settings() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences settings(String s) {
        return context.getSharedPreferences(s, Context.MODE_PRIVATE);
    }

    public JSONObject settingsObject(int name, JSONObject def) {
        String json = settings().getString(context.getString(name), "");
        if (TextUtils.isEmpty(json)) { //
            return def;
        }
        try {
            return new JSONObject(json);
        } catch (Exception e) {
            return def;
        }
    }

    public JSONArray settingsArray(int name, JSONArray def) {
        String json = settingsString(name, "");
        if (TextUtils.isEmpty(json)) { //
            return def;
        }
        try {
            return new JSONArray(json);
        } catch (Exception e) {
            return def;
        }
    }

    public String settingsString(int name, String def) {
        return settings().getString(context.getString(name), def);
    }

    public boolean settingsBoolean(int name, boolean def) {
        return settings().getBoolean(context.getString(name), def);
    }

    public void stringSettings(int name, String value) {
        settings().edit().putString(context.getString(name), value).apply();
    }

    public void booleanSettings(int name, boolean value) {
        settings().edit().putBoolean(context.getString(name), value).apply();
    }

    public void arraySettings(int name, JSONArray value) {
        stringSettings(name, value.toString());
    }

    public void objectSettings(int name, JSONObject value) {
        stringSettings(name, value.toString());
    }
    public List<String> settingsList(int name) {
        List<String> result = new ArrayList<String>();
        String ids = settingsString(name, "");
        String[] arr = ids.split(" ");
        for (String id : arr) {
            if (id != null && !"".equals(id)) {
                result.add(id);
            }
        }
        return result;
    }

    public void listSettings(int name, List<String> value) {
        StringBuilder sb = new StringBuilder();
        for (String s : value) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(s);
        }
        stringSettings(name, sb.toString());
    }


    public Context context() {
        return context;
    }

}
