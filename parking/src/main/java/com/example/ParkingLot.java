package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private final String lotId;
    private int capacity;
    private int carsParked;
    private Map<String, Car> parkedCars;
    private List<ParkingLotObserver> observers;
    private Map<String, Integer> carLocations;
    private Map<String, LocalDateTime> parkingTimes;
    private boolean wasFull;
    private boolean[] parkingSpaces;
    private boolean[] handicapParkingSpaces;
    private Map<String, String> attendantRecords;
    private Map<Integer, String> parkingRows;

    public ParkingLot(String lotId, int capacity, int handicapSpaces) {
        this.lotId = lotId;
        this.capacity = capacity;
        this.carsParked = 0;
        this.parkedCars = new HashMap<>();
        this.observers = new ArrayList<>();
        this.carLocations = new HashMap<>();
        this.parkingTimes = new HashMap<>();
        this.wasFull = false;
        this.parkingSpaces = new boolean[capacity];
        this.handicapParkingSpaces = new boolean[handicapSpaces];
        this.attendantRecords = new HashMap<>();
        this.parkingRows = new HashMap<>();
    }

    public int getCarsParked()
    {
        return carsParked;
    }

    public void addObserver(ParkingLotObserver observer) {
        observers.add(observer);
    }

    private void notifyFull() {
        for (ParkingLotObserver observer : observers) {
            observer.onParkingLotFull();
        }
    }

    private void notifyAvailable() {
        for (ParkingLotObserver observer : observers) {
            observer.onParkingLotAvailable();
        }
    }

    public boolean parkCar(Car car, String attendantName) {
        if (!isFull()) {
            int spaceNumber = getNextAvailableSpace();
            if (spaceNumber != -1) {
                carsParked++;
                parkedCars.put(car.getLicensePlate(), car);
                parkingTimes.put(car.getLicensePlate(), LocalDateTime.now());
                carLocations.put(car.getLicensePlate(), spaceNumber);
                parkingSpaces[spaceNumber] = true; // Mark this space as occupied
                System.out.println("Car " + car.getLicensePlate() + " parked at space " + spaceNumber + ".");
                attendantRecords.put(car.getLicensePlate(), attendantName);
                parkingRows.put(spaceNumber, determineRowForSpace(spaceNumber));
                return true;
            }
        }
        System.out.println("Parking lot is full. Cannot park " + car.getLicensePlate());
        return false;
    }

    public LocalDateTime getParkingTime(String licensePlate) {
        return parkingTimes.get(licensePlate);
    }

    public boolean unparkCar(String licensePlate) {
        Car car = parkedCars.remove(licensePlate);
        if (car != null) {
            Integer spaceNumber = carLocations.remove(licensePlate);
            if (spaceNumber != null) {
                parkingSpaces[spaceNumber] = false; // Free up the space
            }
            carsParked--;
            System.out.println("Car " + car.getLicensePlate() + " unparked. " +
                    (capacity - carsParked) + " spots available now.");
            parkingTimes.remove(licensePlate); // Remove the parking time entry

            if (wasFull && !isFull()) {
                System.out.println("Parking lot has space available now.");
                wasFull = false;
                notifyAvailable();
            }
            return true;
        } else {
            System.out.println("Car with license plate " + licensePlate + " not found.");
            return false;
        }
    }


    public boolean isFull() {
        return carsParked == capacity;
    }

    public String findCarLocation(String licensePlate) {
        Integer spaceNumber = carLocations.get(licensePlate);
        if (spaceNumber != null) {
            return "Your car is parked at space " + spaceNumber + ".";
        } else {
            return "Car not found in the parking lot.";
        }
    }

    public String getLotId() {
        return lotId;
    }

    public boolean isCarParked(String licensePlate) {
        return parkedCars.containsKey(licensePlate);
    }

    public int getNextAvailableSpace() {
        for (int i = 0; i < capacity; i++) {
            if (!parkingSpaces[i]) {
                return i; // Return the index of the first available space
            }
        }
        return -1; // Indicate that no spaces are available
    }

    public int getNearestHandicapSpace() {
        for (int i = 0; i < handicapParkingSpaces.length; i++) {
            if (!handicapParkingSpaces[i]) {
                return i; // Return index of nearest available handicap space
            }
        }
        return -1; // Indicate that no handicap spaces are available
    }

    public int getAvailableSpaces() {
        return capacity - carsParked;
    }

    public List<String> findCarsByColor(String color) {
        List<String> locations = new ArrayList<>();
        for (Map.Entry<String, Car> entry : parkedCars.entrySet()) {
            Car car = entry.getValue();
            if (color.equalsIgnoreCase(car.getColor())) {
                locations.add("Lot " + lotId + " Space " + carLocations.get(entry.getKey()));
            }
        }
        return locations;
    }

    public List<String> findCarsByMakeAndColor(String make, String color) {
        List<String> details = new ArrayList<>();
        for (Map.Entry<String, Car> entry : parkedCars.entrySet()) {
            Car car = entry.getValue();
            if (make.equalsIgnoreCase(car.getMake()) && color.equalsIgnoreCase(car.getColor())) {
                String attendantName = attendantRecords.get(entry.getKey());
                details.add("Lot " + lotId + " Space " + carLocations.get(entry.getKey()) +
                        ", Plate: " + entry.getKey() + ", Attendant: " + attendantName);
            }
        }
        return details;
    }

    public List<String> findCarsByMake(String make) {
        List<String> carDetails = new ArrayList<>();
        for (Map.Entry<String, Car> entry : parkedCars.entrySet()) {
            Car car = entry.getValue();
            if (make.equalsIgnoreCase(car.getMake())) {
                carDetails.add("Lot " + lotId + " Space " + carLocations.get(entry.getKey()) + ", Plate: " + entry.getKey());
            }
        }
        return carDetails;
    }

    public Map<String, LocalDateTime> getParkingTimes() {
        return parkingTimes;
    }

    private String determineRowForSpace(int spaceNumber) {
        // Logic to determine row based on space number (e.g., odd numbers in row B, even in row D)
        return spaceNumber % 2 == 0 ? "D" : "B";
    }

    public List<String> findSmallHandicapCarsInRows(String row1, String row2) {
        List<String> details = new ArrayList<>();
        for (Map.Entry<String, Car> entry : parkedCars.entrySet()) {
            Car car = entry.getValue();
            if ("Small".equalsIgnoreCase(car.getSize()) && car.isHandicap()) {
                Integer spaceNumber = carLocations.get(entry.getKey());
                String parkedRow = parkingRows.get(spaceNumber);
                if (row1.equals(parkedRow) || row2.equals(parkedRow)) {
                    details.add("Lot " + lotId + " Space " + spaceNumber + ", Plate: " + entry.getKey());
                }
            }
        }
        return details;
    }

    public List<String> getAllParkedCarsInfo() {
        List<String> carDetails = new ArrayList<>();
        for (Map.Entry<String, Car> entry : parkedCars.entrySet()) {
            Car car = entry.getValue();
            String info = "Plate: " + entry.getKey() + ", Make: " + car.getMake() +
                    ", Color: " + car.getColor() + ", Size: " + car.getSize();
            carDetails.add(info);
        }
        return carDetails;
    }
}
