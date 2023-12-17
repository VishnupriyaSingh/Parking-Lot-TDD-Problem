package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ParkingLotManager {
    private List<ParkingLot> parkingLots;

    public ParkingLotManager(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public ParkingLot findLeastOccupiedLot() {
        return parkingLots.stream()
                .min(Comparator.comparingInt(ParkingLot::getCarsParked))
                .orElse(null);
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public ParkingLot findMostVacantLot() {
        return parkingLots.stream()
                .max(Comparator.comparingInt(ParkingLot::getAvailableSpaces))
                .orElse(null);
    }

    public List<String> getLocationsOfWhiteCars() {
        List<String> whiteCarLocations = new ArrayList<>();
        for (ParkingLot lot : parkingLots) {
            whiteCarLocations.addAll(lot.findCarsByColor("white"));
        }
        return whiteCarLocations;
    }

    public List<String> getDetailsOfBlueToyotas() {
        List<String> carDetails = new ArrayList<>();
        for (ParkingLot lot : parkingLots) {
            carDetails.addAll(lot.findCarsByMakeAndColor("toyota", "blue"));
        }
        return carDetails;
    }

    public List<String> getDetailsOfBMWCars() {
        List<String> bmwCarDetails = new ArrayList<>();
        for (ParkingLot lot : parkingLots) {
            bmwCarDetails.addAll(lot.findCarsByMake("BMW"));
        }
        return bmwCarDetails;
    }

    public List<String> getCarsParkedInLast30Minutes() {
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        List<String> recentCarDetails = new ArrayList<>();
        for (ParkingLot lot : parkingLots) {
            for (Map.Entry<String, LocalDateTime> entry : lot.getParkingTimes().entrySet()) {
                if (entry.getValue().isAfter(thirtyMinutesAgo)) {
                    recentCarDetails.add("Lot " + lot.getLotId() + " Plate: " + entry.getKey());
                }
            }
        }
        return recentCarDetails;
    }

    public List<String> getSmallHandicapCarsInRowsBOrD() {
        List<String> carDetails = new ArrayList<>();
        for (ParkingLot lot : parkingLots) {
            carDetails.addAll(lot.findSmallHandicapCarsInRows("B", "D"));
        }
        return carDetails;
    }
}
