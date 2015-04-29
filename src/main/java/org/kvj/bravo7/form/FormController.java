package org.kvj.bravo7.form;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.kvj.bravo7.log.Logger;

import java.util.ArrayList;
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

	public void setView(View view) {
        this.view = view;
    }

    class Pair<T> {
		WidgetBundleAdapter<T> viewAdapter;
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

	private void loadValues(Bundle data) {
		if (null != data) {
			for (String name : pairs.keySet()) {
				Pair pair = pairs.get(name);
				pair.viewAdapter.restore(name, data);
				// Log.i(TAG, "Load: " + name + " = " +
				// pair.viewAdapter.get(name, data));
			}
			wasRestored = data.getBoolean(SAVE_MARK, false);
			logger.d("Loading values from:", data, wasRestored);
		}
        this.values = data;
	}

    public void load(Bundle data) {
        load((Bundle) null, data);
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

	public boolean setOriginalValue(String name, Object value) {
        Pair<Object> p = pairs.get(name);
        if (null == p) {
            return false;
        }
        originalValues.put(name, value);
        return true;
    }

    public boolean setValue(String name, Object value) {
        Pair<Object> p = pairs.get(name);
        if (null == p) {
            return false;
        }
        p.viewAdapter.setWidgetValue(value);
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
//		logger.d("changed:", originalValues);
		for (String name : pairs.keySet()) {
			Pair pair = pairs.get(name);
			Object value = pair.viewAdapter.getWidgetValue();
			Object orig = originalValues.get(name);
			if (pair.viewAdapter.adapter().changed(orig, value)) {
//                logger.d("Changed: ", name, "=", orig != null, "-", value != null);
				return true;
			}
		}
		return false;
	}

	public boolean wasRestored() {
		return wasRestored;
	}
}
