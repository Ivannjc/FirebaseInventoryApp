package sg.edu.rp.c346.firebaseinventoryapp;

import java.io.Serializable;

/**
 * Created by 15017608 on 1/8/2017.
 */

public class Inventory implements Serializable{

    private String id;
    private String name;
    private double unitCost;
    public Inventory(){}

    public Inventory(String name, double unitCost) {

        this.name = name;
        this.unitCost = unitCost;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getUnitCost() {
        return unitCost;
    }
    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }
    @Override
    public String toString() {
        return name;
    }
}
