package com.example.av.test.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.Activities.MainActivity;
import com.example.av.test.R;

import java.util.ArrayList;

/**
 * Dialog to remove a certain quantity of a product after the user consumed it.
 */
public class ConsumeDialog extends DialogFragment{

    private String mGroceryName;
    private Context context;
    private ArrayList<Grocery> mArray = new ArrayList<>();
    private Grocery grocery;
    private NumberPicker mNumberPicker;
    private int quantity = 0;
    private String title;

    public static ConsumeDialog newInstance(String name) {
        ConsumeDialog fragment = new ConsumeDialog();
        Bundle args = new Bundle();
        args.putString("grocery", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        context = getActivity();
        mGroceryName = getArguments().getString("grocery");
        setGrocery();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_quantity, null);
        mNumberPicker = (NumberPicker)view.findViewById(R.id.numberPickerQuantity);
        //Initialize the number picker
        numberPicker(mNumberPicker);
        //Create the title
        title = getResources().getString(R.string.consumeTitle);
        title += " " + mGroceryName;
        //Create the dialog
        Dialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.Ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                quantity = mNumberPicker.getValue();
                                updateQuantity(grocery, quantity);
                            }
                        })
                .setNegativeButton(R.string.Cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .create();
        return alertDialog;
    }

    /**
     * Function used to determine which product has been selected by the user
     * According to the name of the product selected we get the object of the product.
     */
    public void setGrocery(){
        mArray = MainActivity.getGroceries();
        for(Grocery g : mArray){
            if(g.getName().equals(mGroceryName)){
                grocery = g;
            }
        }
    }

    public void updateQuantity(Grocery g, int quantity){
        if(g.getQuantity() >= quantity){
            g.setQuantity(g.getQuantity() - quantity);
            GroceryDAO.update(context, g);
            MainActivity.refresh();
        }else{
            Toast.makeText(context, "You don't have enough " +
                    g.getName() + " anymore", Toast.LENGTH_SHORT).show();
        }
    }

    public void numberPicker(NumberPicker np){
        //Create an array with the values of the number picker
        String[] nums = new String[50];
        for(int i=0; i<nums.length; i++) {
            nums[i] = Integer.toString(i);
        }
        //Put the numbers in the number picker
        np.setMinValue(0);
        np.setMaxValue(49);
        np.setWrapSelectorWheel(true);
        np.setDisplayedValues(nums);
        np.setValue(1);
    }
}
