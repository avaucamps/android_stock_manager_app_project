package com.example.av.test.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.av.test.Fragments.AddRemoveActivity.NameSortFragment;
import com.example.av.test.Fragments.AddRemoveActivity.NoSortFragment;
import com.example.av.test.Fragments.AddRemoveActivity.TypeSortFragment;

/**
 * Adapter for the AddRemoveActivity.
 */
public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                NoSortFragment ns = NoSortFragment.newInstance();
                return ns;
            case 1 :
                NameSortFragment nm = NameSortFragment.newInstance();
                return nm;
            case 2 :
                TypeSortFragment t = TypeSortFragment.newInstance();
                return t;
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
