package com.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Create parking lots and parking lot manager
        ParkingLot lot1 = new ParkingLot("Lot1", 10, 2);
        ParkingLot lot2 = new ParkingLot("Lot2", 10, 2);
        List<ParkingLot> lots = Arrays.asList(lot1, lot2);
        ParkingLotManager lotManager = new ParkingLotManager(lots);

        // 2. Create a parking attendant
        ParkingAttendant attendant = new ParkingAttendant(lotManager, "John Doe");

        // 3. Add observers for parking lot full and available notifications
        addObserversToParkingLots(lots);

        // 4. Park some cars, including regular, large, and handicap cars
        parkSomeCars(attendant);

        // 5. Demonstrate finding car locations
        findCarLocations(attendant);

        // 6. Unpark some cars to demonstrate parking lot available notifications
        unparkSomeCars(attendant);

        // 7. Show parking duration for some cars
        showParkingDuration(lot1);

        // 8. Police investigations:
        // a. Show all parked white cars (for bomb threat)
        showParkedWhiteCars(lotManager);

        // b. Show all parked blue Toyotas (for robbery case)
        showParkedBlueToyotas(lotManager);

        // c. Show cars parked in the last 30 minutes (for bomb threat)
        showRecentlyParkedCars(lotManager);

        // d. Show small handicap cars on rows B or D (for permit fraud)
        showSmallHandicapCarsInRowsBOrD(lotManager);

        // e. Show all cars parked on a lot (for fraudulent plate numbers)
        showAllParkedCars(lot1);
    }

    private static void addObserversToParkingLots(List<ParkingLot> lots) {
        for (ParkingLot lot : lots) {
            lot.addObserver(new ParkingLotObserver() {
                @Override
                public void onParkingLotFull() {
                    System.out.println("Notification: " + lot.getLotId() + " is full.");
                }

                @Override
                public void onParkingLotAvailable() {
                    System.out.println("Notification: Space available in " + lot.getLotId() + ".");
                }
            });
        }
    }

    private static void parkSomeCars(ParkingAttendant attendant) {
        // Park a mix of cars
        attendant.parkCar(new Car("CAR001", "Blue", "Toyota", "Medium", false));
        attendant.parkCar(new Car("CAR002", "White", "BMW", "Large", false));
        attendant.parkCar(new Car("CAR003", "Red", "Ford", "Small", false));
        attendant.parkCar(new Car("HND001", "Black", "Honda", "Small", true)); // Handicap car
        attendant.parkCar(new Car("HND002", "blue", "toyota", "Small", true)); // Handicap car
    }

    private static void findCarLocations(ParkingAttendant attendant) {
        System.out.println(attendant.findCarLocation("CAR001"));
        System.out.println(attendant.findCarLocation("HND001"));
    }

    private static void unparkSomeCars(ParkingAttendant attendant) {
        attendant.unparkCar("CAR001");
        attendant.unparkCar("CAR002");
    }

    private static void showParkingDuration(ParkingLot lot) {
        LocalDateTime parkedAt = lot.getParkingTime("CAR002");
        if (parkedAt != null) {
            LocalDateTime now = LocalDateTime.now();
            long secondsParked = Duration.between(parkedAt, now).getSeconds();
            long minutesParked = secondsParked / 60;
            System.out.println("CAR002 has been parked for " + minutesParked + " minutes.");
        } else {
            System.out.println("Parking time for CAR002 is not available.");
        }
    }

    private static void showParkedWhiteCars(ParkingLotManager lotManager) {
        System.out.println("Police Investigation: White Cars");
        List<String> whiteCarDetails = lotManager.getLocationsOfWhiteCars();
        whiteCarDetails.forEach(System.out::println);
    }

    private static void showParkedBlueToyotas(ParkingLotManager lotManager) {
        System.out.println("Police Investigation: Blue Toyota Cars");
        List<String> blueToyotaDetails = lotManager.getDetailsOfBlueToyotas();
        blueToyotaDetails.forEach(System.out::println);
    }

    private static void showRecentlyParkedCars(ParkingLotManager lotManager) {
        System.out.println("Police Investigation: Recently Parked Cars");
        List<String> recentCarDetails = lotManager.getCarsParkedInLast30Minutes();
        recentCarDetails.forEach(System.out::println);
    }

    private static void showSmallHandicapCarsInRowsBOrD(ParkingLotManager lotManager) {
        System.out.println("Police Investigation: Small Handicap Cars in Rows B or D");
        List<String> handicapCarDetails = lotManager.getSmallHandicapCarsInRowsBOrD();
        handicapCarDetails.forEach(System.out::println);
    }

    private static void showAllParkedCars(ParkingLot lot) {
        System.out.println("Police Investigation: All Parked Cars in " + lot.getLotId());
        List<String> parkedCarDetails = lot.getAllParkedCarsInfo();
        parkedCarDetails.forEach(System.out::println);
    }
}
