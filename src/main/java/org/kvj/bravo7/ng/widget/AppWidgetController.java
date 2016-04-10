package org.kvj.bravo7.ng.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import org.kvj.bravo7.log.Logger;
import org.kvj.bravo7.ng.conf.Configurable;
import org.kvj.bravo7.ng.conf.Configurator;
import org.kvj.bravo7.ng.conf.SharedPreferencesConfigurable;
import org.kvj.bravo7.util.Compat;

/**
 * Created by vorobyev on 7/17/15.
 */
public class AppWidgetController {

    Logger logger = Logger.forInstance(this);
    private final AppWidgetManager manager;
    private final Context context;

    private AppWidgetController(Context context) {
        this.context = context;
        this.manager = AppWidgetManager.getInstance(context);
    }

    public static AppWidgetController instance(Context context) {
        return new AppWidgetController(context);
    }

    public int[] ids(Class<? extends AppWidgetProvider> providerClass) {
        return manager.getAppWidgetIds(new ComponentName(context, providerClass));
    }

    public Configurator configurator(final int id) {
        SharedPreferences pref = context.getSharedPreferences(widgetPrefName(id), Context.MODE_PRIVATE);
        Configurable configurable = new SharedPreferencesConfigurable(context, pref);
        return new Configurator(configurable);
    }

    String widgetPrefName(int id) {
        return String.format("widget_config_%d", id);
    }

    public void save(final int id, final Configurable configurable) {
        // No-op for now
    }

    public boolean update(int id, RemoteViews views) {
        if (null == views) return false;
        manager.updateAppWidget(id, views);
        return true;
    }

    public void notify(final int id, final int notifyID) {
        Compat.levelAware(11, new Runnable() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void run() {
                manager.notifyAppWidgetViewDataChanged(id, notifyID);
            }
        });
    }

    public boolean update(final int id, AppWidget.AppWidgetUpdate update) {
        return update(id, update.update(this, id));
    }

    public boolean updateAll(AppWidget update) {
        int[] ids = ids(update.getClass());
        for (int id : ids) { // Update every widget
            update(id, update.updater());
        }
        return true;
    }

    public RemoteViews create(int layout) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), layout);
        return rv;
    }

    public void remove(int id) {
        // Clear config
        logger.d("Removed widget:", id);
        SharedPreferences pref = context.getSharedPreferences(widgetPrefName(id), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit().clear();
        Compat.levelAware(9, new Runnable() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void run() {
                editor.apply();
            }
        }, new Runnable() {
            @Override
            public void run() {
                editor.commit();
            }
        });
    }

    public Intent configIntent(int id) {
        AppWidgetProviderInfo info = manager.getAppWidgetInfo(id);
        if (null == info || null == info.configure) {
            return null;
        }
        Intent intent = new Intent();
        intent.setData(Uri.fromParts("content", String.valueOf(id), null));
        intent.setComponent(info.configure);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        intent.setAction("android.appwidget.action.APPWIDGET_CONFIGURE");
        return intent;
    }

    public Intent remoteIntent(int id, Class<?> svcClass) {
        Intent intent = new Intent(context, svcClass);
        intent.setData(Uri.fromParts("content", String.valueOf(id), null));
        return intent;
    }

    public PendingIntent refreshPendingIntent(int id, Class<? extends AppWidgetRemote.AppWidgetRemoteService> svcClass) {
        Intent intent = remoteIntent(id, svcClass);
        intent.putExtra("action", "refresh");
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public PendingIntent configPendingIntent(int id) {
        return PendingIntent.getActivity(context, 0, configIntent(id), PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public String title(int id, AppWidget widget) {
        return widget.title(this, id);
    }

    public PendingIntent activityPending(Intent launchIntent) {
        return PendingIntent.getActivity(context, 0, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
