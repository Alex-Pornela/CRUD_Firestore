package com.activity.crud.Model;

import java.util.Date;

public class Account {
    private String id;
    private String Name;
    private int totalAmount;
    private String itemName;
    private String Date;
    private Double itemPrice;
    private int Amount;

    Account(){
    }

    //Main list
    public Account(String id, String Name, int totalAmount,int Amount) {
        this.id = id;
        this.Name = Name;
        this.totalAmount = totalAmount;
        this.Amount = Amount;
    }
    //Account info
    public Account(String id, String Name, int totalAmount, String itemName, String date, Double itemPrice, int Amount) {
        this.id = id;
        this.Name = Name;
        this.totalAmount = totalAmount;
        this.itemName = itemName;
        this.Date = Date;
        this.itemPrice = itemPrice;
        this.Amount = Amount;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
