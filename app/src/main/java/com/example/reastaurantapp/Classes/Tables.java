package com.example.reastaurantapp.Classes;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Tables
{
    private String tableid;
    private String tablename;
    private String smokingtype;
    private String windowtype;

    public Tables()
    {

    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getSmokingtype() {
        return smokingtype;
    }

    public void setSmokingtype(String smokingtype) {
        this.smokingtype = smokingtype;
    }

    public String getWindowtype() {
        return windowtype;
    }

    public void setWindowtype(String windowtype) {
        this.windowtype = windowtype;
    }

}
