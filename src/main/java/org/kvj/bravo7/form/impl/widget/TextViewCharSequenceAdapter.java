package org.kvj.bravo7.form.impl.widget;

import android.os.Bundle;
import android.widget.TextView;

import org.kvj.bravo7.form.ViewBundleAdapter;
import org.kvj.bravo7.form.impl.bundle.CharSequenceBundleAdapter;

/**
 * Created by kvorobyev on 4/28/15.
 */
public class TextViewCharSequenceAdapter extends ViewBundleAdapter<TextView, CharSequence> {

    public TextViewCharSequenceAdapter(int resID, String def) {
        super(new CharSequenceBundleAdapter(), resID, def);
    }

    @Override
    public CharSequence getWidgetValue(Bundle bundle) {
        return getView().getText().toString();
    }

    @Override
    public void setWidgetValue(CharSequence value, Bundle bundle) {
        getView().setText(value);
    }
}
