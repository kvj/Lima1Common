package org.kvj.bravo7.ng;

import android.app.Application;

/**
 * Created by kvorobyev on 4/8/15.
 */
abstract public class App<C extends Controller> extends Application {

    private static App instance = null;
    private C controller = null;

    abstract protected C create();

    protected void init() {
    }

    @Override
    public void onCreate() {
        App.instance = this;
        super.onCreate();
        controller = create();
        init();
        controller.init();
    }

    public static App app() {
        return instance;
    }

    public static <C extends Controller> C controller() {
        return (C) instance.controller;
    }
}
