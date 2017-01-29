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

import com.example.av.test.Activities.MainActivity;
import com.example.av.test.DatabaseHelper;
import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.R;

/**
 * Activity used to create a brand new product.
 */

public class NewProductActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private EditText etName;
    private Spinner spinnerTypes;
    private EditText etCriQty;
    private EditText etQty;
    private CheckBox cbMorning;
    private CheckBox cbNoon;
    private CheckBox cbEvening;
    private CheckBox cbOther;
    private RadioGroup rdGroup;
    private RadioButton rbYes;
    private RadioButton rbNo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        getSupportActionBar().setTitle(R.string.create_title);
        myDB = DatabaseHelper.getInstance(this);
        momentOfDay = getIntent().getIntExtra("moment", 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Spinner action
        Resources res = getResources();
        spinnerTypes = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(adapter);
        //EditText and checkbox
        etName = (EditText) findViewById(R.id.etName);
        etCriQty = (EditText) findViewById(R.id.etCriticalQuantity);
        etCriQty.setText("0");
        etQty = (EditText) findViewById(R.id.etQuantity);
        etQty.setText("0");
        cbMorning = (CheckBox) findViewById(R.id.cbMorning);
        cbNoon = (CheckBox) findViewById(R.id.cbNoon);
        cbEvening = (CheckBox) findViewById(R.id.cbEvening);
        cbOther = (CheckBox) findViewById(R.id.cbOther);
        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);
        rdGroup.check(R.id.rbYes);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        MainActivity.refresh();
    }

    public void create(View v){
        if(etName.getText().toString().equals("") || etCriQty.getText().toString().equals("")
                || etQty.getText().toString().equals("") || (cbMorning.isChecked() == false
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
            quantity = Integer.parseInt(etQty.getText().toString());
            morning = cbMorning.isChecked();
            noon = cbNoon.isChecked();
            evening = cbEvening.isChecked();
            other = cbOther.isChecked();
            selected = rbYes.isChecked();
            GroceryDAO.create(this, new Grocery(0, name, type, quantity, critical_quantity, morning,
                    noon, evening, other, selected));
            this.finish();
        }
    }

    public void quit(View v){
        this.finish();
    }
}
