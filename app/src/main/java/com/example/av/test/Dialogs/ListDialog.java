package com.example.av.test.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.R;

import java.util.ArrayList;

/**
 * Dialog that shows all the products whose quantity is critical,
 * meaning all the product whose quantity is inferior or equal to the critical quantity
 * selected by the user.
 */
public class ListDialog extends DialogFragment {

    private static Context context;
    private CharSequence[] cs_products;
    private ArrayList<Grocery> array_products = new ArrayList();
    private ArrayList<String> mNames = new ArrayList<>();

    public ListDialog(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();
        getCriticalProducts();
        cs_products = mNames.toArray(new CharSequence[mNames.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.ListProducts)
                .setItems(cs_products, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    public void getCriticalProducts(){
        array_products = GroceryDAO.getGroceries(context);
        for(Grocery g : array_products){
            if(g.isSelected() == true && g.getQuantity() <= g.getCritical_quantity()){
                mNames.add(g.getName());
            }
        }
    }

}
