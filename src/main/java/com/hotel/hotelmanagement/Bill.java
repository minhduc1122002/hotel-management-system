package com.hotel.hotelmanagement;

public class Bill {
    private int billID;
    private String customerName;
    private int customerID;
    private String date;
    private int amount;
    private int roomNumber;

    public Bill(int billID, String customerName, int customerID, String date, int amount, int roomNumber) {
        this.billID = billID;
        this.customerName = customerName;
        this.customerID = customerID;
        this.date = date;
        this.amount = amount;
        this.roomNumber = roomNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getAmount() {
        return amount;
    }

    public int getBillID() {
        return billID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getDate() {
        return date;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}

