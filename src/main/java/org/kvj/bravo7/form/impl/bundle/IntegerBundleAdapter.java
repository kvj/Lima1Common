package org.kvj.bravo7.form.impl.bundle;

import android.os.Bundle;

import org.kvj.bravo7.form.BundleAdapter;

public class IntegerBundleAdapter extends BundleAdapter<Integer> {

	@Override
	public Integer get(Bundle bundle, String name, Integer def) {
		return bundle.getInt(name, def);
	}

	@Override
	public void set(Bundle bundle, String name, Integer value) {
		bundle.putInt(name, value);
	}

}
