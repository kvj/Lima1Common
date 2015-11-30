package org.kvj.bravo7.form;

import android.os.Bundle;

abstract public class BundleAdapter<T> {

    abstract public T get(Bundle bundle, String name, T def);

    abstract public void set(Bundle bundle, String name, T value);

    public boolean changed(T orig, T value) {
        if (null != value && null != orig && !value.equals(orig)) { // Default impl
            return true;
        }
        return false;
    }
}
