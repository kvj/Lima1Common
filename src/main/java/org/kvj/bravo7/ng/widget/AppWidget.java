package org.kvj.bravo7.ng.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Created by vorobyev on 7/17/15.
 */
abstract public class AppWidget extends AppWidgetProvider {

    public String title(AppWidgetController controller, int id) {
        return String.format("Widget '%d'", id);
    }

    public interface AppWidgetUpdate {
        public RemoteViews update(AppWidgetController controller, int id);
    }

    abstract public AppWidgetUpdate updater();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        AppWidgetController controller = AppWidgetController.instance(context);
        for (int id : appWidgetIds) {
            RemoteViews views = updater().update(controller, id);
            controller.update(id, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        AppWidgetController controller = AppWidgetController.instance(context);
        for (int id : appWidgetIds) {
            controller.remove(id);
        }
        super.onDeleted(context, appWidgetIds);
    }
}
