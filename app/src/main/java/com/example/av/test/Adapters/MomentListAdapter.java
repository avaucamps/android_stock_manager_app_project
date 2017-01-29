package com.example.av.test.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.av.test.Grocery;
import com.example.av.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the MomentFragment
 */
public class MomentListAdapter extends ArrayAdapter {
    Context context;
    List<Grocery> mGroceries = new ArrayList<>();
    private int p;


    public MomentListAdapter(Context context, int resource, List<Grocery> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mGroceries = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.p = position;
        final Grocery g = mGroceries.get(p);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_stock, parent, false);
        final TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        final TextView tvQty = (TextView) convertView.findViewById(R.id.tvQuantity);
        tvName.setText(g.getName());
        tvQty.setText(String.valueOf(g.getQuantity()));
        return convertView;
    }
}
