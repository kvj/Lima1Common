package org.kvj.bravo7.form;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.kvj.bravo7.form.impl.ViewFinder;
import org.kvj.bravo7.log.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FormController {

    private static final String SAVE_MARK = "$$SAVE_MARK$$";
    private Bundle values = null;
    private Logger logger = Logger.forInstance(this);
    private boolean wasRestored = false;

    public <T extends View> T getView(String key) {
        return (T)getAdapter(key, ViewBundleAdapter.class).getView();
    }

    public <T extends View> T getView(String key, Class<T> cl) {
        return (T)getAdapter(key, ViewBundleAdapter.class).getView();
    }

    class Pair<T> {

        WidgetBundleAdapter<T> viewAdapter;
    }

    private static final String TAG = "Form";

    private Map<String, Pair> pairs = new LinkedHashMap<String, Pair>();
    private Map<String, Object> originalValues = new HashMap<String, Object>();
    protected final ViewFinder viewFinder;

    public FormController(ViewFinder viewFinder) {
        this.viewFinder = viewFinder;
    }

    public <V, T> void add(WidgetBundleAdapter<T> viewAdapter, String name) {
        Pair pair = new Pair();
        pair.viewAdapter = viewAdapter;
        pairs.put(name, pair);
        viewAdapter.key = name;
        viewAdapter.setController(this);
    }

    private void loadDefaultValues(Bundle values, List<String> namesSearch) {
        for (String name : pairs.keySet()) {
            if (!namesSearch.contains(name)) continue;
            Pair pair = pairs.get(name);
            originalValues.put(name, pair.viewAdapter.get(name, values));
        }
    }

    public void setAsOriginal() {
        originalValues.clear();
        for (String name : pairs.keySet()) {
            Pair pair = pairs.get(name);
            originalValues.put(name, pair.viewAdapter.getWidgetValue());
        }
    }

    public boolean loadOne(String name) {
        Pair pair = pairs.get(name);
        if (values != null && pair != null) {
            pair.viewAdapter.restore(name, values);
            return true;
        }
        return false;
    }

    private void loadValues(Bundle data, List<String> namesSearch) {
        if (null != data) {
            for (String name : pairs.keySet()) {
                Pair pair = pairs.get(name);
                if (!namesSearch.contains(name)) continue;
                pair.viewAdapter.restore(name, data);
                // Log.i(TAG, "Load: " + name + " = " +
                // pair.viewAdapter.get(name, data));
            }
            wasRestored = data.getBoolean(SAVE_MARK, false);
            logger.d("Loading values from:", data, wasRestored);
        }
        this.values = data;
    }

    public Bundle load(Activity activity, Bundle data, String... names) {
        List<String> namesSearch = new ArrayList<>();
        if (null != names && names.length > 0) {
            Collections.addAll(namesSearch, names);
        } else {
            namesSearch.addAll(pairs.keySet());
        }
        Bundle values = new Bundle();
        if (null == activity) {
            // already loaded by activity
            values = data;
        } else {
            if (null != activity.getIntent() && null != activity.getIntent().getExtras()) {
                values = activity.getIntent().getExtras();
            }
        }
        loadDefaultValues(values, namesSearch);
        if (null == data) {
            data = values;
        }
        // Set values to views
        loadValues(data, namesSearch);
        return data;
    }

    public void save(Bundle data, String... names) {
        save(data, false, names);
    }

    public void save(Bundle data, boolean forLoad, String... names) {
        List<String> namesSearch = new ArrayList<>();
        if (null != names) {
            Collections.addAll(namesSearch, names);
        }
        for (String name : pairs.keySet()) {
            Pair pair = pairs.get(name);
            if (!namesSearch.isEmpty() && !namesSearch.contains(name)) { // Names mode - ignore
                continue;
            }
            if (pair.viewAdapter.oneShow()) {
                continue;
            }
            pair.viewAdapter.save(name, data);
        }
        if (!forLoad) {
            data.putBoolean(SAVE_MARK, true);
        }
    }

    public <T> T getValue(String name, Class<T> cl) {
        Pair p = pairs.get(name);
        if (null == p) {
            return null;
        }
        return (T) p.viewAdapter.getWidgetValue();
    }

    public <T> T getValue(String name) {
        Pair p = pairs.get(name);
        if (null == p) {
            return null;
        }
        return (T) p.viewAdapter.getWidgetValue();
    }

    public boolean setValue(String name, Object value) {
        return setValue(name, value, false);
    }

    public boolean setValue(String name, Object value, boolean asOriginal) {
        Pair<Object> p = pairs.get(name);
        if (null == p) {
            return false;
        }
        p.viewAdapter.setWidgetValue(value);
        if (asOriginal) {
            originalValues.put(name, value);
            p.viewAdapter.asOriginal(value);
        }
        return true;
    }

    public <T extends WidgetBundleAdapter<?>> T getAdapter(String name, Class<T> cl) {
        Pair p = pairs.get(name);
        if (null == p) {
            return null;
        }
        return (T) p.viewAdapter;
    }

    public boolean changed() {
//        logger.d("changed:", originalValues);
        for (String name : pairs.keySet()) {
            Pair pair = pairs.get(name);
            Object value = pair.viewAdapter.getWidgetValue();
            Object orig = originalValues.get(name);
            if (pair.viewAdapter.changed(orig, value)) {
//                logger.d("Changed: ", name, "=", orig != null, "-", value != null);
                return true;
            }
        }
        return false;
    }

    public boolean wasRestored() {
        return wasRestored;
    }

    public Collection<String> changes(String... names) {
        List<String> result = new ArrayList<>();
        List<String> namesSearch = new ArrayList<>();
        if (null != names) {
            Collections.addAll(namesSearch, names);
        }
        for (String name : pairs.keySet()) {
            Pair pair = pairs.get(name);
            if (!namesSearch.isEmpty() && !namesSearch.contains(name)) { // Names mode - ignore
                continue;
            }
            Object value = pair.viewAdapter.getWidgetValue();
            Object orig = originalValues.get(name);
            if (pair.viewAdapter.adapter().changed(orig, value)) {
                result.add(name);
            }
        }
        return result;
    }
}
