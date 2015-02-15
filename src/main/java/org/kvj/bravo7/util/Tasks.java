package org.kvj.bravo7.util;

import android.os.AsyncTask;

/**
 * Created by kvorobyev on 2/15/15.
 */
public class Tasks {

    abstract public static class SimpleTask<T> extends AsyncTask<Void, Void, T> {

        @Override
        protected T doInBackground(Void... params) {
            return doInBackground();
        }

        abstract protected T doInBackground();

        public SimpleTask<T> exec() {
            return (SimpleTask<T>) this.execute();
        }
    }

    abstract public static class VerySimpleTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            doInBackground();
            return null;
        }

        abstract protected void doInBackground();

        public VerySimpleTask exec() {
            return (VerySimpleTask) this.execute();
        }
    }

}
