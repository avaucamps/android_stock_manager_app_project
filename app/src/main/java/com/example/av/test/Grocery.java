package com.example.av.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Creates a grocery item
 */
public class Grocery implements Serializable{

    private int id;
    private String name;
    private String type;
    private int quantity;
    private int critical_quantity;
    private boolean morning;
    private boolean noon;
    private boolean evening;
    private boolean other;
    private boolean selected;


    //Constructor
    public Grocery(int id, String name, String type, int quantity, int critical_quantity,
                   boolean morning, boolean noon, boolean evening, boolean other, boolean selected){
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.critical_quantity = critical_quantity;
        this.morning = morning;
        this.noon = noon;
        this.evening = evening;
        this.other = other;
        this.selected = selected;
    }

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCritical_quantity() {
        return critical_quantity;
    }

    public void setCritical_quantity(int critical_quantity) {
        this.critical_quantity = critical_quantity;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isNoon() {
        return noon;
    }

    public void setNoon(boolean noon) {
        this.noon = noon;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
