package com.example.av.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * All the functions related to the database for groceries
 * Pattern DAO
 */
public class GroceryDAO {

    //table product
    public static final String GROCERY_TABLE_NAME = "product";
    public static final String GROCERY_COLUMN_ID = "id";
    public static final String GROCERY_COLUMN_NAME = "name";
    public static final String GROCERY_COLUMN_TYPE = "type";
    public static final String GROCERY_COLUMN_QUANTITY = "quantity";
    public static final String GROCERY_COLUMN_CRITICAL_QUANTITY = "critical_quantity";
    public static final String GROCERY_COLUMN_MORNING = "morning";
    public static final String GROCERY_COLUMN_NOON = "noon";
    public static final String GROCERY_COLUMN_EVENING = "evening";
    public static final String GROCERY_COLUMN_OTHER = "other";
    public static final String GROCERY_COLUMN_SELECTED = "selected";
    public static DatabaseHelper myDB;

    //Database methods
    public static String createTable() {
        String query = "create table " + GROCERY_TABLE_NAME + "(" + GROCERY_COLUMN_ID +
                " integer primary key autoincrement, " + GROCERY_COLUMN_NAME + " text, "
                + GROCERY_COLUMN_TYPE + " text," + GROCERY_COLUMN_QUANTITY + " int, " +
                GROCERY_COLUMN_CRITICAL_QUANTITY + " int, " + GROCERY_COLUMN_MORNING + " boolean, " +
                GROCERY_COLUMN_NOON + " boolean, " + GROCERY_COLUMN_EVENING + " boolean, " +
                GROCERY_COLUMN_OTHER + " boolean, " + GROCERY_COLUMN_SELECTED + " boolean)";
        return query;
    }

    public static String dropTable() {
        String query = "DROP TABLE IF EXISTS " + GROCERY_TABLE_NAME;
        return query;
    }

    public static void create(Context context, Grocery grocery){
        myDB = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = myDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GROCERY_COLUMN_NAME, grocery.getName());
        contentValues.put(GROCERY_COLUMN_TYPE, grocery.getType());
        contentValues.put(GROCERY_COLUMN_QUANTITY, grocery.getQuantity());
        contentValues.put(GROCERY_COLUMN_CRITICAL_QUANTITY, grocery.getCritical_quantity());
        contentValues.put(GROCERY_COLUMN_MORNING, grocery.isMorning());
        contentValues.put(GROCERY_COLUMN_NOON, grocery.isNoon());
        contentValues.put(GROCERY_COLUMN_EVENING, grocery.isEvening());
        contentValues.put(GROCERY_COLUMN_OTHER, grocery.isOther());
        contentValues.put(GROCERY_COLUMN_SELECTED, grocery.isSelected());
        db.insert(GROCERY_TABLE_NAME, null, contentValues);
    }

    public static void delete(Context context, Grocery grocery){
        myDB = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = myDB.getWritableDatabase();
        db.delete(GROCERY_TABLE_NAME, GROCERY_COLUMN_ID + " = ? ",
                new String[]{Integer.toString(grocery.getId())});
    }

    public static ArrayList<Grocery> getGroceries(Context context){
        ArrayList<Grocery> array_grocery = new ArrayList<>();
        myDB = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = myDB.getReadableDatabase();
        String query = "Select * from product ORDER BY " + GROCERY_COLUMN_NAME;
        Cursor res =  db.rawQuery( query, null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_grocery.add(new Grocery(res.getInt(res.getColumnIndex(GROCERY_COLUMN_ID)),
                    res.getString(res.getColumnIndex(GROCERY_COLUMN_NAME)),
                    res.getString(res.getColumnIndex(GROCERY_COLUMN_TYPE)),
                    res.getInt(res.getColumnIndex(GROCERY_COLUMN_QUANTITY)),
                    res.getInt(res.getColumnIndex(GROCERY_COLUMN_CRITICAL_QUANTITY)),
                    res.getInt(res.getColumnIndex(GROCERY_COLUMN_MORNING)) >  0,
                    res.getInt(res.getColumnIndex(GROCERY_COLUMN_NOON)) > 0,
                    res.getInt(res.getColumnIndex(GROCERY_COLUMN_EVENING)) > 0,
                    res.getInt(res.getColumnIndex(GROCERY_COLUMN_OTHER)) > 0,
                    res.getInt(res.getColumnIndex(GROCERY_COLUMN_SELECTED)) > 0));
            res.moveToNext();
        }
        return array_grocery;
    }

    public static void update(Context context, Grocery grocery){
        myDB = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = myDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GROCERY_COLUMN_NAME, grocery.getName());
        contentValues.put(GROCERY_COLUMN_TYPE, grocery.getType());
        contentValues.put(GROCERY_COLUMN_QUANTITY, grocery.getQuantity());
        contentValues.put(GROCERY_COLUMN_CRITICAL_QUANTITY, grocery.getCritical_quantity());
        contentValues.put(GROCERY_COLUMN_MORNING, grocery.isMorning());
        contentValues.put(GROCERY_COLUMN_NOON, grocery.isNoon());
        contentValues.put(GROCERY_COLUMN_EVENING, grocery.isEvening());
        contentValues.put(GROCERY_COLUMN_OTHER, grocery.isOther());
        contentValues.put(GROCERY_COLUMN_SELECTED, grocery.isSelected());
        db.update(GROCERY_TABLE_NAME, contentValues, GROCERY_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(grocery.getId()) } );
    }
}
