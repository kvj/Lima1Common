package org.kvj.bravo7.form.impl.bundle;

import android.os.Bundle;

import org.kvj.bravo7.form.BundleAdapter;

public class BooleanBundleAdapter extends BundleAdapter<Boolean> {

	@Override
	public Boolean get(Bundle bundle, String name, Boolean def) {
		return bundle.getBoolean(name, def);
	}

	@Override
	public void set(Bundle bundle, String name, Boolean value) {
		bundle.putBoolean(name, value);
	}

}
