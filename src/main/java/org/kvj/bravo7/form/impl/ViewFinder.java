package org.kvj.bravo7.form.impl;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by kvorobyev on 11/21/15.
 */
public interface ViewFinder {

    public static class ActivityViewFinder implements ViewFinder {

        private final Activity activity;

        public ActivityViewFinder(Activity activity) {
            this.activity = activity;
        }

        @Override
        public View findViewById(int id) {
            return activity.findViewById(id);
        }
    }

    public static class FragmentViewFinder implements ViewFinder {

        private final Fragment fragment;

        public FragmentViewFinder(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public View findViewById(int id) {
            return fragment.getView().findViewById(id);
        }
    }

    public View findViewById(int id);
}


