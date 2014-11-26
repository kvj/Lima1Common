package org.kvj.bravo7.form;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class FormController {

    private Bundle values = null;

    class Pair {
		WidgetBundleAdapter<?> viewAdapter;
	}

	private static final String TAG = "Form";

	private Map<String, Pair> pairs = new LinkedHashMap<String, Pair>();
	private Map<String, Object> originalValues = new HashMap<String, Object>();
	protected View view;

	public FormController(View view) {
		this.view = view;
	}

	public <V, T> void add(WidgetBundleAdapter<T> viewAdapter, String name) {
		Pair pair = new Pair();
		pair.viewAdapter = viewAdapter;
		pairs.put(name, pair);
		viewAdapter.key = name;
		viewAdapter.setController(this);
	}

	private void loadDefaultValues(Bundle values) {
		originalValues.clear();
		for (String name : pairs.keySet()) {
			Pair pair = pairs.get(name);
			originalValues.put(name, pair.viewAdapter.get(name, values));
			// Log.i(TAG, "Load origins: " + name + " = " +
			// pair.viewAdapter.get(name, values));
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

	private void loadValues(Bundle data) {
		if (null != data) {
			for (String name : pairs.keySet()) {
				Pair pair = pairs.get(name);
				pair.viewAdapter.restore(name, data);
				// Log.i(TAG, "Load: " + name + " = " +
				// pair.viewAdapter.get(name, data));
			}
		}
        this.values = data;
	}

	public void load(Bundle dialogArguments, Bundle data) {
		Bundle values = new Bundle();
		if (null != dialogArguments) {
			values = dialogArguments;
		}
		loadDefaultValues(values);
		if (null == data) {
			data = values;
		}
		// Set values to views
		loadValues(data);
	}

	public void load(Activity activity, Bundle data) {
		Bundle values = new Bundle();
		if (null != activity.getIntent() && null != activity.getIntent().getExtras()) {
			values = activity.getIntent().getExtras();
		}
		loadDefaultValues(values);
		if (null == data) {
			data = values;
		}
		// Set values to views
		loadValues(data);
	}

	public void save(Bundle data) {
		for (String name : pairs.keySet()) {
			Pair pair = pairs.get(name);
			pair.viewAdapter.save(name, data);
		}
	}

	public <T> T getValue(String name, Class<T> cl) {
		Pair p = pairs.get(name);
		if (null == p) {
			return null;
		}
		return (T) p.viewAdapter.getWidgetValue();
	}

	public <T extends WidgetBundleAdapter<?>> T getAdapter(String name, Class<T> cl) {
		Pair p = pairs.get(name);
		if (null == p) {
			return null;
		}
		return (T) p.viewAdapter;
	}

	public boolean changed() {
		for (String name : pairs.keySet()) {
			Pair pair = pairs.get(name);
			Object value = pair.viewAdapter.getWidgetValue();
			Object orig = originalValues.get(name);
			if (null != value && null != orig && !value.equals(orig)) {
                Log.i(TAG, "Load: " + name + " = " + orig + " - " + value);
				return true;
			}
		}
		return false;
	}
}
