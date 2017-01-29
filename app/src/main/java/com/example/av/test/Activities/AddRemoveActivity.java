package com.example.av.test.Activities;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.Fragments.AddRemoveActivity.NameSortFragment;
import com.example.av.test.Fragments.AddRemoveActivity.NoSortFragment;
import com.example.av.test.R;
import com.example.av.test.Fragments.AddRemoveActivity.TypeSortFragment;
import com.example.av.test.Fragments.AddRemoveActivity.ViewPagerAddRemove;

import java.util.ArrayList;

/**
 * Class used to add or remove a product into the users's stock.
 */

public class AddRemoveActivity extends AppCompatActivity
        implements NoSortFragment.OnFragmentInteractionListener,
        NameSortFragment.OnFragmentInteractionListener,
        TypeSortFragment.OnFragmentInteractionListener,
        ViewPagerAddRemove.OnFragmentInteractionListener {

    public static ArrayList<Grocery> mSelected = new ArrayList<>();
    public static ArrayList<Grocery> mDeselected = new ArrayList<>();
    private static Context context;
    private static PagerAdapter mPagerAdapter;
    private static RadioGroup radioGroup;
    private static RadioButton rdNo;
    private static RadioButton rdName;
    private static RadioButton rdType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.add_remove_title);
        context = this;
        radioGroup = (RadioGroup) findViewById(R.id.rdGroup);
        rdNo = (RadioButton) findViewById(R.id.rdNo);
        rdName = (RadioButton) findViewById(R.id.rdName);
        rdType = (RadioButton) findViewById(R.id.rdType);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);
                ViewPagerAddRemove.getViewPager().setCurrentItem(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        MainActivity.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_remove, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                ViewPagerAddRemove.refresh();
                return true;
            default:
                // If we get here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            ViewPagerAddRemove vp = new ViewPagerAddRemove();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, vp);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    public void quit(View v){
        this.finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public static void addItem(Grocery g){
        g.setSelected(true);
        GroceryDAO.update(context, g);
    }

    public static void removeItem(Grocery g){
        g.setQuantity(0);
        g.setSelected(false);
        GroceryDAO.update(context, g);
    }

    public static void checkButton(int position){
        switch(position){
            case 0 :
                rdNo.setChecked(true);
                break;
            case 1 :
                rdName.setChecked(true);
                break;
            case 2 :
                rdType.setChecked(true);
                break;
            default :
                rdNo.setChecked(true);
        }
    }
}
