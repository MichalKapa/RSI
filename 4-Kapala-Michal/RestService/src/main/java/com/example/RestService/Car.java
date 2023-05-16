package com.example.RestService;

public class Car {
    private int id;
    private String brand;
    private int year;
    private String color;
    private boolean isNew;
    public enum CarStatus {
        LISTED,
        NOT_LISTED,
        RENTED
    }
    private CarStatus status;
    public Car() {
    }
    public Car(int id, String brand, int year, String color, boolean isNew, CarStatus status) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.color = color;
        this.isNew = isNew;
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public boolean getIsNew() { return isNew; }
    public void setIsNew(boolean isNew) { this.isNew = isNew; }
    public CarStatus getStatus() {
        return status;
    }
    public void setStatus(CarStatus status) {
        this.status = status;
    }

}