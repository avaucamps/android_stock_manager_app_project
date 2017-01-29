package com.example.av.test.Activities;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.av.test.DatabaseHelper;
import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity used to edit a product registered by the user.
 */

public class EditActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private EditText etName;
    private Spinner spinnerTypes;
    private EditText etCriQty;
    private TextView etQuantity;
    private CheckBox cbMorning;
    private CheckBox cbNoon;
    private CheckBox cbEvening;
    private CheckBox cbOther;
    private RadioButton rbYes;
    private RadioButton rbNo;
    private RadioGroup rdGroup;
    private List<String> mTypes = new ArrayList();
    private String name;
    private String type;
    private int critical_quantity;
    private int quantity;
    private boolean morning;
    private boolean noon;
    private boolean evening;
    private boolean other;
    private int momentOfDay;
    private boolean selected;
    private Grocery g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        g = (Grocery) getIntent().getSerializableExtra("grocery");
        String title = getString(R.string.edit_title);
        title += " " + g.getName();
        getSupportActionBar().setTitle(title);
        myDB = DatabaseHelper.getInstance(this);
        momentOfDay = getIntent().getIntExtra("moment", 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Spinner action
        Resources res = getResources();
        spinnerTypes = (Spinner) findViewById(R.id.spinnerType);
        mTypes = Arrays.asList(res.getStringArray(R.array.types));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(adapter);
        //EditText and checkbox
        etName = (EditText) findViewById(R.id.etName);
        etCriQty = (EditText) findViewById(R.id.etCriticalQuantity);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        cbMorning = (CheckBox) findViewById(R.id.cbMorning);
        cbNoon = (CheckBox) findViewById(R.id.cbNoon);
        cbEvening = (CheckBox) findViewById(R.id.cbEvening);
        cbOther = (CheckBox) findViewById(R.id.cbOther);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);
        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);
        setInformation();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        MainActivity.refresh();
    }

    public void setInformation(){
        etName.setText(g.getName());
        int posType = 0;
        for(int i = 0; i <= mTypes.size()-1; i++){
            if(mTypes.get(i).equals(g.getType())){
                posType = i;
            }
        }
        spinnerTypes.setSelection(posType);
        etCriQty.setText(String.valueOf(g.getCritical_quantity()));
        etQuantity.setText(String.valueOf(g.getQuantity()));
        cbMorning.setChecked(g.isMorning());
        cbNoon.setChecked(g.isNoon());
        cbEvening.setChecked(g.isEvening());
        cbOther.setChecked(g.isOther());
        if(g.isSelected()){
            rdGroup.check(R.id.rbYes);
        }else{
            rdGroup.check(R.id.rbNo);
        }
    }

    public void create(View v){
        if(etName.getText().toString().equals("") || etCriQty.getText().toString().equals("")
                || etQuantity.getText().toString().equals("") || (cbMorning.isChecked() == false
                && cbNoon.isChecked() == false
                &&  cbEvening.isChecked() == false && cbOther.isChecked() == false)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.error_message)
                    .setMessage(R.string.missing_information)
                    .setNegativeButton(R.string.Ok, null)
                    .show();
        }else {
            type = spinnerTypes.getSelectedItem().toString();
            name = etName.getText().toString();
            critical_quantity = Integer.parseInt(etCriQty.getText().toString());
            quantity = Integer.parseInt(etQuantity.getText().toString());
            morning = cbMorning.isChecked();
            noon = cbNoon.isChecked();
            evening = cbEvening.isChecked();
            other = cbOther.isChecked();
            selected = rbYes.isChecked();
            g.setName(name);
            g.setType(type);
            g.setQuantity(quantity);
            g.setCritical_quantity(critical_quantity);
            g.setSelected(selected);
            g.setEvening(evening);
            g.setMorning(morning);
            g.setNoon(noon);
            g.setOther(other);
            GroceryDAO.update(this, g);
            this.finish();
            MainActivity.refresh();
        }
    }

    public void quit(View v){
        this.finish();
    }
}
