package com.example.av.test.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.av.test.Fragments.MomentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the MainActivity.
 */
public class MomentPagerAdapter extends FragmentPagerAdapter {
    private List<String> tabTitles = new ArrayList();
    private Context context;

    public MomentPagerAdapter(FragmentManager fm, List<String> tabTitles, Context context) {
        super(fm);
        this.tabTitles = tabTitles;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return MomentFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
