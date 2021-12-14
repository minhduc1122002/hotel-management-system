package com.hotel.hotelmanagement;

public class Reservation {

    private int resID;

    private int roomNumber;

    private String customerName;

    private String checkInDate;

    private String checkOutDate;

    private int totalDays;

    private int totalPrice;

    private String status;

    public Reservation(int resID, int roomNumber, String customerName, String checkInDate, String checkOutDate, int totalDays, int totalPrice, String status) {
        this.roomNumber = roomNumber;
        this.customerName = customerName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalDays = totalDays;
        this.totalPrice = totalPrice;
        this.resID = resID;
        this.status = status;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStatus() {
        return status;
    }

    public int getResID() {
        return resID;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}
