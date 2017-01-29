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
import android.widget.Button;
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
 * Class to sort products by type
 */

public class TypeSearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static ListView listItems;
    private static ArrayList<Grocery> mArray = new ArrayList<>();
    private Button btnClose;
    private static String mType;

    public TypeSearchFragment() {
    }

    public static TypeSearchFragment newInstance() {
        TypeSearchFragment fragment = new TypeSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type_search, container, false);
        listItems = (ListView) view.findViewById(R.id.listItemsTypeSearch);
        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.closeTypeFragment();
            }
        });
        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        listItems.setEmptyView(emptyText);
        setListeners();
        registerForContextMenu(listItems);
        return view;
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

    public void setListeners(){
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.setGrocery(mArray.get(position));
            }
        });
        listItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.setGrocery(mArray.get(position));
                return false;
            }
        });
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

    public static void search(String type, Context context){
        mArray.clear();
        mType = type;
        ArrayList<Grocery> mGroceries = new ArrayList<>();
        mGroceries = MainActivity.getGroceries();
        for(Grocery g : mGroceries){
            if(g.getType().equals(type) && g.isSelected())
                mArray.add(g);
        }
        MomentListAdapter adapter = new MomentListAdapter(context, 0, mArray);
        listItems.setAdapter(adapter);
    }

    public static ListView getListItems(){
        return listItems;
    }

    public static String getType(){
        return mType;
    }
}
