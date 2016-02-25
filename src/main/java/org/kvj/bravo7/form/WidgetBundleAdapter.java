package org.kvj.bravo7.form;

import android.os.Bundle;

import org.kvj.bravo7.log.Logger;

abstract public class WidgetBundleAdapter<T> {

    private static final String ORIGIN = "_$origin";
    protected T defaultValue;
    protected T originalValue = null;
    private BundleAdapter<T> adapter;
    protected FormController controller = null;
    protected String key = null;
    protected Logger logger = Logger.forInstance(this);
    private boolean oneShot = false;

    public WidgetBundleAdapter(BundleAdapter<T> adapter, T def) {
        this.defaultValue = def;
        this.adapter = adapter;
    }

    public T save(String name, Bundle bundle) {
        T value = getWidgetValue(bundle);
        adapter.set(bundle, name, value);
        if (trackOrigin && originalValue != null) {
            adapter.set(bundle, name+ORIGIN, originalValue);
        }
        return value;
    }

    public T getWidgetValue() {
        return getWidgetValue(null);
    }

    public abstract T getWidgetValue(Bundle bundle);

    public void restore(String name, Bundle bundle) {
        setWidgetValue(get(name, bundle), bundle);
        if (trackOrigin && bundle.containsKey(name+ORIGIN)) {
            originalValue = get(name+ORIGIN, bundle);
        }
    }

    public void setWidgetValue(T value) {
        setWidgetValue(value, null);
    }

    public abstract void setWidgetValue(T value, Bundle bundle);

    public T get(String name, Bundle data) {
        return adapter.get(data, name, defaultValue);
    }

    public void setController(FormController controller) {
        this.controller = controller;
    }


    public BundleAdapter<T> adapter() {
        return adapter;
    }

    protected boolean trackOrigin = false;

    public WidgetBundleAdapter<T> trackOrigin(boolean trackOrigin) {
        this.trackOrigin = trackOrigin;
        return this;
    }

    public boolean trackOrigin() {
        return trackOrigin;
    }

    public boolean changed(T orig, T value) {
        if (trackOrigin && null != originalValue) {
            return adapter().changed(originalValue, value);
        }
        return adapter().changed(orig, value);
    }

    public void asOriginal(T value) {
        if (trackOrigin) {
            originalValue = value;
        }
    }

    public WidgetBundleAdapter<T> oneShot() {
        this.oneShot = true;
        return this;
    }

    public boolean oneShow() {
        return oneShot;
    }
}
