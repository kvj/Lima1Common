package org.kvj.bravo7.form.impl.bundle;

import android.os.Bundle;

import org.kvj.bravo7.form.BundleAdapter;

public class StringBundleAdapter extends BundleAdapter<String> {

	@Override
	public String get(Bundle bundle, String name, String def) {
		String value = bundle.getString(name);
		if (null == value) {
			return def;
		}
		return value;
	}

	@Override
	public void set(Bundle bundle, String name, String value) {
		bundle.putString(name, value);
	}

}
