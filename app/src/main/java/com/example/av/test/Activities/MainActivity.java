package com.example.av.test.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.av.test.Dialogs.ListDialog;
import com.example.av.test.Dialogs.TypeDialog;
import com.example.av.test.Grocery;
import com.example.av.test.GroceryDAO;
import com.example.av.test.Fragments.MomentFragment;
import com.example.av.test.Fragments.NameSearchFragment;
import com.example.av.test.R;
import com.example.av.test.Fragments.TypeSearchFragment;
import com.example.av.test.Fragments.ViewPagerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MomentFragment.OnFragmentInteractionListener,
        NameSearchFragment.OnFragmentInteractionListener,
        ViewPagerFragment.OnFragmentInteractionListener,
        TypeSearchFragment.OnFragmentInteractionListener {

    private static int momentOfDay;
    private static Context context;
    private static Grocery grocery;
    private static ArrayList<Grocery> mGroceries = new ArrayList();
    private static String searchedName;
    private NameSearchFragment nameSearchFragment;
    private ViewPagerFragment viewPagerFragment;
    private static TypeSearchFragment typeSearchFragment;
    private static FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_title);
        context = this;
        momentOfDay = 0;
        setGroceries();
        viewPagerFragment = new ViewPagerFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, viewPagerFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //----------------NAVIGATION DRAWER---------------------------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Give the TabLayout the ViewPager
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        System.exit(0);
    }

    //--------------------SEARCH VIEW ACTION BAR----------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search),
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        nameSearchFragment = new NameSearchFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.container, nameSearchFragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.commit();
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        ViewPagerFragment.refresh();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.remove(nameSearchFragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.addToBackStack(null);
                        ft.commit();
                        return true;
                    }
                });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                NameSearchFragment.search(query, context);
                searchedName = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(NameSearchFragment.getListItems()!=null){
                    NameSearchFragment.search(newText, context);
                    searchedName = newText;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_sort_by_type:
                TypeDialog td = TypeDialog.newInstance();
                td.show(getSupportFragmentManager(), "choose_type");
                typeSearchFragment = new TypeSearchFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.container, typeSearchFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.addToBackStack(null);
                ft.commit();
                this.fragmentTransaction = getSupportFragmentManager().beginTransaction();
                return true;
            default:
                // If we get here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_add_remove) {
            Intent intent = new Intent(this, AddRemoveActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_shopping_list) {
            ListDialog ld = new ListDialog();
            ld.show(getFragmentManager(), "my_tag");
        } else if (id == R.id.nav_create_new) {
            Intent intent = new Intent(this, NewProductActivity.class);
            intent.putExtra("moment", momentOfDay);
            startActivity(intent);
        } else if (id == R.id.nav_delete) {
            delete();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }


    /**
     * Function that shows a dialog to confirm suppression of a product
     */
    public void delete(){
            new AlertDialog.Builder(this)
                    .setTitle(grocery.getName())
                    .setMessage(R.string.DeleteMessage)
                    .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GroceryDAO.delete(context, grocery);
                            refresh();
                        }
                    })
                    .setNegativeButton(R.string.Cancel, null)
                    .show();
    }

    /**
     * Function to refresh all the fragments
     */
    public static void refresh(){
        setGroceries();
        ViewPagerFragment.refresh();
        if(NameSearchFragment.getListItems()!=null){
            NameSearchFragment.search(searchedName, context);
        }
        if(TypeSearchFragment.getListItems()!=null){
            TypeSearchFragment.search(TypeSearchFragment.getType(), context);
        }
    }

    /**
     * Function to get an item selected in the listView
     */
    public static Grocery getGrocery(){
        ArrayList<Grocery> mArray = new ArrayList();
        mArray = GroceryDAO.getGroceries(context);
        for(Grocery g : mArray){
            if(grocery.getName().equals(g.getName())){
                return g;
            }
        }
        return null;
    }

    //Set the selected grocery in the listView
    public static void setGrocery(Grocery g){
        grocery = g;
    }

    public static ArrayList<Grocery> getGroceries(){
        return mGroceries;
    }

    public static void setGroceries(){
        mGroceries = GroceryDAO.getGroceries(context);
    }

    /**
     * Closes the fragment that contains the types
     */
    public static void closeTypeFragment(){
        ViewPagerFragment.refresh();
        fragmentTransaction.remove(typeSearchFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
