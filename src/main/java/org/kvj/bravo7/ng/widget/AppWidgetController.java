package org.kvj.bravo7.ng.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import org.kvj.bravo7.ng.conf.BundleConfigurable;
import org.kvj.bravo7.ng.conf.Configurable;
import org.kvj.bravo7.ng.conf.SharedPreferencesConfigurable;
import org.kvj.bravo7.util.Compat;

/**
 * Created by vorobyev on 7/17/15.
 */
public class AppWidgetController {

    private final AppWidgetManager manager;
    private final Context context;

    private AppWidgetController(Context context) {
        this.context = context;
        this.manager = AppWidgetManager.getInstance(context);
    }

    public static AppWidgetController instance(Context context) {
        return new AppWidgetController(context);
    }

    public int[] ids(Class<AppWidgetProvider> providerClass) {
        return manager.getAppWidgetIds(new ComponentName(context, providerClass));
    }

    public Configurable configurable(final int id) {
        return Compat.produceLevelAware(Build.VERSION_CODES.JELLY_BEAN,
                                        new Compat.Producer<Configurable>() {
                                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                            @Override
                                            public Configurable produce() {
                                                return new BundleConfigurable(context, manager
                                                    .getAppWidgetOptions(id));
                                            }
                                        }, new Compat.Producer<Configurable>() {
                @Override
                public Configurable produce() {
                    return new SharedPreferencesConfigurable(context, PreferenceManager
                        .getDefaultSharedPreferences(context));
                }
            });
    }

    public void save(final int id, final Configurable configurable) {
        Compat.levelAware(Build.VERSION_CODES.JELLY_BEAN, new Runnable() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                manager.updateAppWidgetOptions(id, ((BundleConfigurable) configurable).bundle());
            }
        });
    }


    public boolean update(int id, RemoteViews views) {
        if (null == views) return false;
        manager.updateAppWidget(id, views);
        return true;
    }

    public boolean update(int id, AppWidget.AppWidgetUpdate update) {
        return update(id, update.update(this, id));
    }

    public RemoteViews create(int layout) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), layout);
        return rv;
    }
}
