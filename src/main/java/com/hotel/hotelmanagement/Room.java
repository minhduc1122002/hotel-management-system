package com.hotel.hotelmanagement;

public class Room {
    private int number;

    private int price;

    private String type;

    private String status;

    public Room(int roomNumber, int price, String roomType, String status) {
        this.number = roomNumber;
        this.price = price;
        this.type = roomType;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }
}
