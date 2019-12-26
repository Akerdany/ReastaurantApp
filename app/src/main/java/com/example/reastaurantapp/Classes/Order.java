package com.example.reastaurantapp.Classes;

import com.example.reastaurantapp.Classes.Food;

import java.util.ArrayList;

public class Order {
    private String OrderNumber;

    private ArrayList<Food> Items;

    public Order(){
    }

    public Order(String orderNumber, ArrayList<Food> items) {
        OrderNumber = orderNumber;
        Items = items;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public ArrayList<Food> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Food> items) {
        Items = items;
    }
}
