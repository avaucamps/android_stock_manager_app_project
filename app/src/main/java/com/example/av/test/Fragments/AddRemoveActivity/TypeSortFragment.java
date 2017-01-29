package com.example.av.test.Fragments.AddRemoveActivity;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.av.test.Adapters.CustomAdapter;
import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.R;

import java.util.ArrayList;


/**
 * Fragment used to sort products by type
 */
public class TypeSortFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int fragment2;
    private ArrayList<Grocery> mGroceries = new ArrayList();
    private Spinner spinnerType;
    private ListView listItems;

    private OnFragmentInteractionListener mListener;

    public TypeSortFragment() {
        // Required empty public constructor
    }

    public static TypeSortFragment newInstance() {
        TypeSortFragment fragment = new TypeSortFragment();
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
        //ListView Stuff
        View view = inflater.inflate(R.layout.fragment_sort_type, container, false);
        listItems = (ListView) view.findViewById(R.id.listItems);
        mGroceries = new ArrayList();
        mGroceries = populateList(listItems, "No Type");
        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        listItems.setEmptyView(emptyText);
        //Check Items already in stock
        //Spinner Stuff
        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
        setSpinner();
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

    public ArrayList<Grocery> populateList(ListView listItems, String type){
        ArrayList<Grocery> mArrayGroceries = new ArrayList<>();
        ArrayList<Grocery> mGrocerySelected = new ArrayList<>();
        mArrayGroceries = GroceryDAO.getGroceries(getActivity());
        if(type.equals("No Type")){
            for(Grocery g : mArrayGroceries){
                mGrocerySelected.add(g);
            }
        }else{
            for(Grocery g : mArrayGroceries){
                if(g.getType().equals(type)){
                    mGrocerySelected.add(g);
                }
            }
        }
        mArrayGroceries = GroceryDAO.getGroceries(getActivity());
        CustomAdapter adapter= new CustomAdapter(getActivity(), 2, mGrocerySelected);
        listItems.setAdapter(adapter);
        return mArrayGroceries;
    }

    public void setSpinner(){
        Resources res = getResources();
        final ArrayAdapter<String> mArrayTypes = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.types));
        mArrayTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(mArrayTypes);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mType = mArrayTypes.getItem(position);
                mGroceries = populateList(listItems, mType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
