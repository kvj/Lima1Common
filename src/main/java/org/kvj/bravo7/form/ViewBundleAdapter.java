package org.kvj.bravo7.form;

import android.view.View;

public abstract class ViewBundleAdapter<V extends View, T> extends WidgetBundleAdapter<T> {

    V view = null;
    protected int resID;
    public V getView() {
        if (null == view) {
            view = (V) controller.viewFinder.findViewById(resID);
        }
        return view;
    }

    public ViewBundleAdapter(BundleAdapter<T> adapter, int resID, T def) {
        super(adapter, def);
        this.resID = resID;
    }

}
