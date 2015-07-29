package org.kvj.bravo7.ng.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import org.kvj.bravo7.log.Logger;

/**
 * Created by vorobyev on 7/27/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
abstract public class AppWidgetConfigActivity extends AppCompatActivity {

    Logger logger = Logger.forInstance(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppWidgetConfigFragment fragment = new AppWidgetConfigFragment();
        logger.d("Configuring:", id(), preferenceXML());
        fragment.preferenceXML(AppWidgetController.instance(this).widgetPrefName(id()), preferenceXML());
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    protected int id() {
        Bundle extras = getIntent().getExtras();
        int id = AppWidgetManager.INVALID_APPWIDGET_ID;
        if (null != extras) {
            id = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        }
        return id;
    }

    protected boolean validID(int id) {
        return id != AppWidgetManager.INVALID_APPWIDGET_ID;
    }

    protected abstract int preferenceXML();
    protected abstract AppWidget.AppWidgetUpdate appWidget();

    @Override
    public void finish() {
        int id = id();
        if (validID(id)) {
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            setResult(RESULT_OK, resultValue);
            AppWidgetController.instance(this).update(id, appWidget());
        }
        super.finish();
    }
}
