package com.example.reastaurantapp.Classes;

import com.example.reastaurantapp.Classes.Food;

import java.util.ArrayList;

public class Order {
    private int OrderNumber;

    private ArrayList<Food> Items;

    public Order(int orderNumber, ArrayList<Food> items) {
        OrderNumber = orderNumber;
        Items = items;
    }

    public int getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        OrderNumber = orderNumber;
    }

    public ArrayList<Food> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Food> items) {
        Items = items;
    }
}
