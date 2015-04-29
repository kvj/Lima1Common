package org.kvj.bravo7.form.impl.bundle;

import android.os.Bundle;

import org.kvj.bravo7.form.BundleAdapter;

public class LongBundleAdapter extends BundleAdapter<Long> {

	@Override
	public Long get(Bundle bundle, String name, Long def) {
		return bundle.getLong(name, def);
	}

	@Override
	public void set(Bundle bundle, String name, Long value) {
		bundle.putLong(name, value);
	}

}
