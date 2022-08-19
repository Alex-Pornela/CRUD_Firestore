package com.activity.crud.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Account implements Serializable {
    private String id;
    private String Name;
    private int totalAmount;
    private String itemName;
    private String Date;
    private Double itemPrice;
    private int Amount;
    private String itemId;
    private  String itemDate;
    List<Account> accountList;

    public Account(){
    }

    public Account(List<Account> accountList){
        this.accountList = accountList;
    }

    //Main list
    public Account(String id, String Name, int totalAmount,int Amount) {
        this.id = id;
        this.Name = Name;
        this.totalAmount = totalAmount;
        this.Amount = Amount;
    }
    //Account info
    public Account(String itemName, String itemId, int Amount, String itemDate) {
        this.id = itemId;
        this.itemName = itemName;
        this.Amount = Amount;
        this.itemId = itemId;
        this.itemDate = itemDate;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
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
