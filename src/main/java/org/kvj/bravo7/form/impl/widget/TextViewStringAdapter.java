package org.kvj.bravo7.form.impl.widget;

import android.os.Bundle;
import android.widget.TextView;

import org.kvj.bravo7.form.ViewBundleAdapter;
import org.kvj.bravo7.form.impl.bundle.StringBundleAdapter;

public class TextViewStringAdapter extends ViewBundleAdapter<TextView, String> {

    private boolean loaded = false;

	public TextViewStringAdapter(int resID, String def) {
		super(new StringBundleAdapter(), resID, def);
	}

	@Override
	public String getWidgetValue(Bundle bundle) {
//        logger.d("get", defaultValue, loaded);
        if (!loaded) { // No data has been set
            return defaultValue;
        }
		return getView().getText().toString().trim();
	}

	@Override
	public void setWidgetValue(String value, Bundle bundle) {
        if (defaultValue != value) { // Different
            loaded = true;
        }
//        logger.d("set", value, defaultValue, loaded);
		getView().setText(value);
	}

}
