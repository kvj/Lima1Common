package org.kvj.bravo7.ng.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.kvj.bravo7.log.Logger;

/**
 * Created by vorobyev on 7/28/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AppWidgetRemote {

    abstract public static class AppWidgetRemoteService<T extends AppWidget.AppWidgetUpdate> extends RemoteViewsService {

        protected Logger logger = Logger.forInstance(this);

        @Override
        public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
            return adapter().init(getApplicationContext(), intent);
        }

        abstract protected AppWidgetRemoteAdapter adapter();

        abstract protected T widgetUpdate();

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if ("refresh".equals(intent.getStringExtra("action"))) { //
                int id = Integer.valueOf(intent.getData().getSchemeSpecificPart());
                AppWidgetController controller = AppWidgetController.instance(this);
                controller.update(id, widgetUpdate());
                return START_NOT_STICKY;
            }
            return super.onStartCommand(intent, flags, startId);
        }
    }

    abstract public static class AppWidgetRemoteAdapter implements RemoteViewsService.RemoteViewsFactory {

        protected Logger logger = Logger.forInstance(this);
        protected Context context = null;
        protected int id = AppWidgetManager.INVALID_APPWIDGET_ID;
        protected AppWidgetController controller = null;

        protected AppWidgetRemoteAdapter init(Context context, Intent intent) {
            this.context = context;
            this.id = Integer.valueOf(intent.getData().getSchemeSpecificPart());
            this.controller = AppWidgetController.instance(context);
            return this;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {
        }

        abstract public int getCount();
        abstract public RemoteViews getViewAt(int i);

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}
