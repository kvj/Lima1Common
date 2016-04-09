package org.kvj.bravo7.ng.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.kvj.bravo7.log.Logger;

/**
 * Created by vorobyev on 7/27/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AppWidgetConfigFragment extends PreferenceFragment {

    Logger logger = Logger.forInstance(this);

    private int xml = -1;
    private String pref = "";

    public void preferenceXML(String pref, int xml) {
        this.xml = xml;
        this.pref = pref;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.d("Settings here:", xml, pref);
        if (xml != -1) {
            // Has been set
            getPreferenceManager().setSharedPreferencesName(pref);
            addPreferencesFromResource(xml);
            onCreated();
        }
    }

    public void onCreated() {

    }

}
