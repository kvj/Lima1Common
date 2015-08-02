package org.kvj.bravo7.ng.conf;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vorobyev on 7/17/15.
 */
public class Configurator {

    private final Configurable configurable;

    public Configurator(Configurable configurable) {
        this.configurable = configurable;
    }
    public JSONObject settingsObject(int name, JSONObject def) {
        String json = configurable.getString(name, "");
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
        return configurable.getString(name, def);
    }

    public int settingsInt(int name, int def) {
        return configurable.getInt(name, def);
    }

    public boolean settingsBoolean(int name, boolean def) {
        return configurable.getBool(name, def);
    }

    public void stringSettings(int name, String value) {
        configurable.setString(name, value);
    }

    public void intSettings(int name, int value) {
        configurable.setInt(name, value);
    }

    public void booleanSettings(int name, boolean value) {
        configurable.setBool(name, value);
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
        String[] arr = ids.split("\n");
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
                sb.append("\n");
            }
            sb.append(s);
        }
        stringSettings(name, sb.toString());
    }
}
