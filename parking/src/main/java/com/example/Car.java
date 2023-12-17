package com.example;

public class Car {
    private String licensePlate;
    private String color;
    private String make;
    private String size; // Small, Medium, Large
    private boolean isHandicap;

    public Car(String licensePlate, String color, String make, String size, boolean isHandicap) {
        this.licensePlate = licensePlate;
        this.color = color;
        this.make = make;
        this.size = size;
        this.isHandicap = isHandicap;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getColor() {
        return color;
    }

    public String getMake() {
        return make;
    }

    public String getSize() {
        return size;
    }

    public boolean isHandicap() {
        return isHandicap;
    }
}