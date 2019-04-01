package com.example.Bachelors;

import java.util.Date;

public class Transactions {

    int amount;
    String name;
    String date;
    String direction;

    public Transactions() {}

    public Transactions(int amount, String name, String date,String direction) {
        this.amount = amount;
        this.name = name;
        this.date = date;
        this.direction = direction;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() {return direction;}

    public void setDirection(String direction) {this.direction = direction;}
}
