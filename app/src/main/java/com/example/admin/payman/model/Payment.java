package com.example.admin.payman.model;

/**
 * Created by Admin on 2018-12-05.
 */

public class Payment extends ModelBase {
    private String name;
    private String cost;
    private String date;
    private long status;

    public Payment() {
    }

    public Payment(long id, String name, String cost, String date, long status) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return name + " " + cost;
    }
}
