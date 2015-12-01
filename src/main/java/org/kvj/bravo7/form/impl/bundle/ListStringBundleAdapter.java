package org.kvj.bravo7.form.impl.bundle;

import android.os.Bundle;

import org.kvj.bravo7.form.BundleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vorobyev on 12/1/15.
 */
public class ListStringBundleAdapter extends BundleAdapter<ArrayList<String>> {

    @Override
    public ArrayList<String> get(Bundle bundle, String name, ArrayList<String> def) {
        ArrayList<String> result = bundle.getStringArrayList(name);
        if (null == result) result = def;
        return result;
    }

    @Override
    public void set(Bundle bundle, String name, ArrayList<String> value) {
        bundle.putStringArrayList(name, value);
    }
}
