package org.kvj.bravo7.form.impl.bundle;

import android.os.Bundle;
import android.text.TextUtils;

import org.kvj.bravo7.form.BundleAdapter;
import org.kvj.bravo7.log.Logger;

/**
 * Created by kvorobyev on 4/28/15.
 */
public class CharSequenceBundleAdapter extends BundleAdapter<CharSequence> {

    private Logger logger = Logger.forInstance(this);

    @Override
    public CharSequence get(Bundle bundle, String name, CharSequence def) {
        CharSequence value = bundle.getCharSequence(name);
        if (null == value) {
            return def;
        }
        return value;
    }

    @Override
    public void set(Bundle bundle, String name, CharSequence value) {
        bundle.putCharSequence(name, value);
    }

    @Override
    public boolean changed(CharSequence cs1, CharSequence cs2) {
        boolean emp1 = TextUtils.isEmpty(cs1);
        boolean emp2 = TextUtils.isEmpty(cs2);
//        logger.d("Changed?", emp1, emp2);
        if (emp1 && emp2) { // Both empty
            return false;
        }
        if (!emp1 && !emp2) { // Compare two not empty string
//            logger.d("Changed?", cs1.toString().equals(cs2.toString()), cs1.length(), cs2.length());
            return !cs1.toString().equals(cs2.toString());
        }
        return true;
    }
}
