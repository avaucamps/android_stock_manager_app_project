package com.example.av.test.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.av.test.R;
import com.example.av.test.Fragments.TypeSearchFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dialog which let the user define the product of the product he is creating/editing.
 */
public class TypeDialog extends DialogFragment {

    private List<String> mTypes = new ArrayList();
    private String mTypeSelected;

    public static TypeDialog newInstance() {
        TypeDialog fragment = new TypeDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getTypes();
        CharSequence[] cs = mTypes.toArray(new CharSequence[mTypes.size()]);
        Dialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.TypeTitle)
                .setSingleChoiceItems(cs, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTypeSelected = getTypeSelected(which);
                    }
                })
                .setPositiveButton(R.string.Ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                TypeSearchFragment.search(mTypeSelected, getActivity());

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

    public void getTypes(){
        mTypes = Arrays.asList(getResources().getStringArray(R.array.types));
    }

    public String getTypeSelected(int position){
        String type = mTypes.get(position);
        return type;
    }
}
