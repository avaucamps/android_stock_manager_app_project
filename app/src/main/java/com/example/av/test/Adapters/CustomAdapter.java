package com.example.av.test.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.av.test.Activities.AddRemoveActivity;
import com.example.av.test.Grocery;
import com.example.av.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the sorting fragments for AddRemoveActivity
 */
public class CustomAdapter extends ArrayAdapter {

    Context context;
    List<Grocery> mGroceries = new ArrayList<>();
    private int p;


    public CustomAdapter(Context context, int resource, List<Grocery> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mGroceries = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.p = position;
        final Grocery g = mGroceries.get(p);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_add_remove, parent, false);
        final TextView tvName = (TextView) convertView.findViewById(R.id.name);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
        cb.setTag(position);
        tvName.setText(g.getName());
        if(g.isSelected() == true){
            cb.setChecked(true);
        }
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb.isChecked()){
                        AddRemoveActivity.addItem(g);
                }else{
                        AddRemoveActivity.removeItem(g);
                }
            }
        });
        return convertView;
    }
}
