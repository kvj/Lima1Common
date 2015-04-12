package org.kvj.bravo7.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kvorobyev on 4/11/15.
 */
public class LayoutsPagerAdapter extends FragmentPagerAdapter {

    private final int[] ids;

    public static class LayoutsPagerFragment extends Fragment {

        private LayoutsPagerAdapter adapter = null;
        private int position = 0;

        public LayoutsPagerFragment() {
        }

        public void init(LayoutsPagerAdapter adapter, int position) {
            this.adapter = adapter;
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (null == adapter) {
                return null;
            }
            View view = inflater.inflate(adapter.ids[position], container, false);
            adapter.customize(view, position, adapter.ids[position]);
            return view;
        }
    }

    protected void customize(View view, int position, int layoutID) {
    }

    public LayoutsPagerAdapter(FragmentManager fm, int... ids) {
        super(fm);
        this.ids = ids;
    }

    @Override
    public Fragment getItem(int position) {
        LayoutsPagerFragment fragment = new LayoutsPagerFragment();
        fragment.init(this, position);
        return fragment;
    }

    @Override
    public int getCount() {
        return ids.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = pageTitle(position, ids[position]);
        if (!TextUtils.isEmpty(title)) { // Use provided
            return title;
        }
        return super.getPageTitle(position);
    }

    protected CharSequence pageTitle(int position, int layoutID) {
        return null;
    }
}
