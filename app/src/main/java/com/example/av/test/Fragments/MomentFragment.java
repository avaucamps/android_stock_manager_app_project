package com.example.av.test.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.av.test.Activities.EditActivity;
import com.example.av.test.Activities.MainActivity;
import com.example.av.test.Dialogs.ConsumeDialog;
import com.example.av.test.Dialogs.UpdateQtyDialog;
import com.example.av.test.Grocery;
import com.example.av.test.Adapters.MomentListAdapter;
import com.example.av.test.R;

import java.util.ArrayList;


/**
 * Fragment to sort the groceries according to the moment(s) of the day they are consumed at
 */

public class MomentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int mMoment; //0 = morning, 1 = noon, 2 = evening, 3 = other, 4 = sort by name, 5 = sort by type
    private static ListView listItems;
    private OnFragmentInteractionListener mListener;
    private static ArrayList<Grocery> mMorning = new ArrayList();
    private static ArrayList<Grocery> mNoon = new ArrayList();
    private static ArrayList<Grocery> mEvening = new ArrayList();
    private static ArrayList<Grocery> mOther = new ArrayList();
    private static Context context;

    public MomentFragment() {
        // Required empty public constructor
    }

    public static MomentFragment newInstance(int moment) {
        MomentFragment fragment = new MomentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, moment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMoment = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moment, container, false);
        context = getActivity();
        listItems = (ListView) view.findViewById(R.id.listItems);
        populateList(mMoment);
        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        listItems.setEmptyView(emptyText);
        //listListener(listItems);
        registerForContextMenu(listItems);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menu.setHeaderTitle(MainActivity.getGrocery().getName());
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Grocery g = MainActivity.getGrocery();
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra("grocery", MainActivity.getGrocery());
                startActivity(intent);
                return true;
            case R.id.update_quantity:
                UpdateQtyDialog up = UpdateQtyDialog.newInstance(g.getName());
                up.show(getActivity().getFragmentManager(), "update_quantity");
                return true;
            case R.id.consume:
                ConsumeDialog c = ConsumeDialog.newInstance(g.getName());
                c.show(getActivity().getFragmentManager(), "consume_grocery");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void populateList(int moment){
        ArrayList<Grocery> mArray = new ArrayList<>();
        mArray = getGroceries(moment);
        MomentListAdapter adapter= new MomentListAdapter(context, 0, mArray);
        listItems.setAdapter(adapter);
        listListener(listItems, mArray);
    }

    public void listListener(ListView listItems, final ArrayList<Grocery> mGroceries) {
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.setGrocery(mGroceries.get(position));
            }
        });
        listItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.setGrocery(mGroceries.get(position));
                return false;
            }
        });
    }

    public static ArrayList<Grocery> getGroceries(int moment){
        ArrayList<Grocery> mArray = new ArrayList<>();
        ArrayList<Grocery> mSelected = new ArrayList<>();
        mArray = MainActivity.getGroceries();
        switch(moment) {
            case 0:
                for (Grocery g : mArray) {
                    if (g.isMorning() && g.isSelected()) {
                        mSelected.add(g);
                    }
                }
                break;
            case 1:
                for (Grocery g : mArray) {
                    if (g.isNoon() && g.isSelected()) {
                        mSelected.add(g);
                    }
                }
                break;
            case 2:
                for (Grocery g : mArray) {
                    if (g.isEvening() && g.isSelected()) {
                        mSelected.add(g);
                    }
                }
                break;
            case 3:
                for (Grocery g : mArray) {
                    if (g.isOther() && g.isSelected()) {
                        mSelected.add(g);
                    }
                }
                break;
        }
        return mSelected;
        }
    }
