package com.example.av.test.Fragments.AddRemoveActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.av.test.Activities.AddRemoveActivity;
import com.example.av.test.Adapters.ScreenSlidePagerAdapter;
import com.example.av.test.R;

/**
 * ViewPager (lets you slide between fragments) for the activity to add or remove a product to the
 * stock
 */

public class ViewPagerAddRemove extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static ScreenSlidePagerAdapter mScreenSlide;
    private static ViewPager mViewPager;

    public ViewPagerAddRemove() {
        // Required empty public constructor
    }

    public static ViewPagerAddRemove newInstance() {
        ViewPagerAddRemove fragment = new ViewPagerAddRemove();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager2, container, false);
        mScreenSlide = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mScreenSlide);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                AddRemoveActivity.checkButton(position);
                //mScreenSlide.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static void refresh(){
        mScreenSlide.notifyDataSetChanged();
    }

    public static ViewPager getViewPager(){
        return mViewPager;
    }
}
