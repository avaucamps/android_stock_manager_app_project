package com.example.av.test.Fragments.AddRemoveActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.av.test.Adapters.CustomAdapter;
import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.R;

import java.util.ArrayList;

/**
 * Fragment to show the products sorted by name
 */

public class NameSortFragment extends Fragment {

    private ArrayList<Grocery> mGroceries = new ArrayList();

    private OnFragmentInteractionListener mListener;

    public NameSortFragment() {
        // Required empty public constructor
    }

    public static NameSortFragment newInstance() {
        NameSortFragment fragment = new NameSortFragment();
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
        View view = inflater.inflate(R.layout.fragment_sort_name, container, false);
        //POPULATE LISTVIEW
        final ListView listItems = (ListView) view.findViewById(R.id.listItems);
        mGroceries = new ArrayList();
        mGroceries = populateList(listItems, "");
        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        listItems.setEmptyView(emptyText);
        //checkItems(mGroceries, listItems);
        //EditText stuff
        EditText etName = (EditText) view.findViewById(R.id.etName);
        editTextListener(etName, listItems);
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
        void onFragmentInteraction(Uri uri);

    }

    public ArrayList<Grocery> populateList(ListView listItems, String name){
        ArrayList<Grocery> mArrayGroceries = new ArrayList<>();
        ArrayList<Grocery> mGrocerySelected = new ArrayList<>();
        mArrayGroceries = GroceryDAO.getGroceries(getActivity());
        for(Grocery g : mArrayGroceries){
            if (!name.equals("")) {
                if(g.getName().toUpperCase().matches(name.toUpperCase() + ".*")){
                    mGrocerySelected.add(g);
                }
            }else{
                mGrocerySelected.add(g);
            }
        }
        CustomAdapter adapter= new CustomAdapter(getActivity(), 1, mGrocerySelected);
        listItems.setAdapter(adapter);
        return mArrayGroceries;
    }


    public void editTextListener(EditText etName, final ListView listItems){
        etName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                mGroceries = populateList(listItems, s.toString());
            }
        });
    }
}
