package com.example;

public class ParkingAttendant {
    private String name;
    private ParkingLot parkingLot; // For single parking lot
    private ParkingLotManager lotManager; // For managing multiple lots

    // Constructor for single parking lot
    public ParkingAttendant(ParkingLot parkingLot, String name) {
        this.parkingLot = parkingLot;
        this.name = name;
    }

    // Constructor for multiple parking lots
    public ParkingAttendant(ParkingLotManager lotManager, String name) {
        this.lotManager = lotManager;
        this.name = name;
    }


    public boolean parkCar(Car car) {
        if (lotManager != null) {
            // Parking in the least occupied lot managed by ParkingLotManager
            ParkingLot leastOccupiedLot = lotManager.findLeastOccupiedLot();
            if (leastOccupiedLot != null) {
                return leastOccupiedLot.parkCar(car, this.name);
            }
        } else if (parkingLot != null) {
            // Parking in a specific lot
            return parkingLot.parkCar(car, this.name);
        }
        return false;
    }

//    public boolean parkCar(Car car) {
//        return parkCar(car, null); // Call the main parkCar method with a null space number
//    }

    public String findCarLocation(String licensePlate) {
        if (lotManager != null) {
            for (ParkingLot lot : lotManager.getParkingLots()) {
                String location = lot.findCarLocation(licensePlate);
                if (location != null) {
                    return "Car " + licensePlate + " found in lot " + lot.getLotId();
                }
            }
            return "Car not found in any lot.";
        } else if (parkingLot != null) {
            return parkingLot.findCarLocation(licensePlate);
        }
        return "Car not found.";
    }

    public boolean unparkCar(String licensePlate) {
        if (lotManager != null) {
            for (ParkingLot lot : lotManager.getParkingLots()) {
                if (lot.isCarParked(licensePlate)) {
                    return lot.unparkCar(licensePlate);
                }
            }
            return false; // Car not found in any lot
        } else if (parkingLot != null) {
            return parkingLot.unparkCar(licensePlate);
        }
        return false;
    }

    public boolean parkHandicapCar(Car car) {
        if (lotManager != null) {
            ParkingLot leastOccupiedLot = lotManager.findLeastOccupiedLot();
            if (leastOccupiedLot != null) {
                int nearestHandicapSpace = leastOccupiedLot.getNearestHandicapSpace();
                if (nearestHandicapSpace != -1) {
                    return leastOccupiedLot.parkCar(car, this.name);
                }
            }
        } else if (parkingLot != null) {
            int nearestHandicapSpace = parkingLot.getNearestHandicapSpace();
            if (nearestHandicapSpace != -1) {
                return parkingLot.parkCar(car, this.name);
            }
        }
        return false; // No handicap space available or no parking lot set
    }

    public boolean parkLargeCar(Car car) {
        if (lotManager != null) {
            ParkingLot mostVacantLot = lotManager.findMostVacantLot();
            if (mostVacantLot != null) {
                return mostVacantLot.parkCar(car, this.name);
            }
        } else if (parkingLot != null) {
            // If there's only one lot, park in it
            return parkingLot.parkCar(car, this.name);
        }
        return false; // No suitable lot found
    }
}
