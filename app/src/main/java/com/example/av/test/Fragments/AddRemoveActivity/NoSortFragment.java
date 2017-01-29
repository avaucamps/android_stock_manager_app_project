package com.example.av.test.Fragments.AddRemoveActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.av.test.Adapters.CustomAdapter;
import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.R;

import java.util.ArrayList;

/**
 * Class to get the products without sorting them
 */

public class NoSortFragment extends Fragment {

    private ArrayList<Grocery> mGroceries = new ArrayList();

    private OnFragmentInteractionListener mListener;

    public NoSortFragment() {
        // Required empty public constructor
    }

    public static NoSortFragment newInstance() {
        NoSortFragment fragment = new NoSortFragment();
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
        View view = inflater.inflate(R.layout.fragment_no_sort, container, false);
        //POPULATE LISTVIEW
        ListView listItems = (ListView) view.findViewById(R.id.listItems);
        mGroceries.clear();
        mGroceries = populateList(listItems);
        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        listItems.setEmptyView(emptyText);
        return view;
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

    public ArrayList<Grocery> populateList(ListView listItems){
        ArrayList<Grocery> mArrayGroceries = new ArrayList<>();
        mArrayGroceries = GroceryDAO.getGroceries(getActivity());
        CustomAdapter adapter= new CustomAdapter(getActivity(), 0, mArrayGroceries);
        listItems.setAdapter(adapter);
        return mArrayGroceries;
    }
}
