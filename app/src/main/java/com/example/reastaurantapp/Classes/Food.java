package com.example.reastaurantapp.Classes;

public class Food {

    private String ItemID;
    private String ItemName;
    private String ItemDesc;

    private int ItemPrice;
    private int ItemPhoto;

    public Food(){

    }

    public Food(String itemID, String itemName, String itemDesc, int itemPrice, int itemPhoto) {
        ItemID = itemID;
        ItemName = itemName;
        ItemDesc = itemDesc;
        ItemPrice = itemPrice;
        ItemPhoto = itemPhoto;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public int getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(int itemPrice) {
        ItemPrice = itemPrice;
    }

    public int getItemPhoto() {
        return ItemPhoto;
    }

    public void setItemPhoto(int itemPhoto) {
        ItemPhoto = itemPhoto;
    }
}
