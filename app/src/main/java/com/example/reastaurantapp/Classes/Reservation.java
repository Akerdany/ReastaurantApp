package com.example.reastaurantapp.Classes;

public class Reservation
{
    private String Day;
    private String Month;
    private String Year;
    private String Hour;
    private String tableName;

    public Reservation()
    {
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        this.Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        this.Hour = hour;
    }

    public String getTablename() {
        return tableName;
    }

    public void setTablename(String tablename) {
        this.tableName = tablename;
    }
}
