package com.activity.crud.Model;

import java.util.Date;

public class Account {
    private String id;
    private int No;
    private String Name;
    private Double totalAmount;
    private String itemName;
    private String Date;
    private Double itemPrice;

    Account(){
    }

    //Main list
    public Account(String id, String Name, Double totalAmount,int No) {
        this.id = id;
        this.Name = Name;
        this.totalAmount = totalAmount;
        this.No = No;
    }
    //Account info
    public Account(String id, String Name, Double totalAmount, String itemName, String date, Double itemPrice) {
        this.id = id;
        this.Name = Name;
        this.totalAmount = totalAmount;
        this.itemName = itemName;
        this.Date = Date;
        this.itemPrice = itemPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
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
