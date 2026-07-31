package com.rental;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages the collection of vehicles.
 *
 * ENCAPSULATION: the internal Map is private. Callers can only interact
 * with it through the methods below (addVehicle, findVehicle, rent, etc.),
 * so invariants such as "no two vehicles share a plate number" are always
 * enforced in one place.
 */
public class RentalSystem {

    private final Map<String, Vehicle> vehicles = new LinkedHashMap<>();

    /**
     * Adds a vehicle. Returns false if the plate number is already taken.
     */
    public boolean addVehicle(Vehicle vehicle) {
        String plate = vehicle.getPlateNumber();
        if (vehicles.containsKey(plate)) {
            return false; // duplicate plate number
        }
        vehicles.put(plate, vehicle);
        return true;
    }

    public boolean isDuplicatePlate(String plateNumber) {
        return vehicles.containsKey(plateNumber.toUpperCase());
    }

    public Vehicle findVehicle(String plateNumber) {
        return vehicles.get(plateNumber.toUpperCase());
    }

    public Collection<Vehicle> getAllVehicles() {
        return vehicles.values();
    }

    public boolean isEmpty() {
        return vehicles.isEmpty();
    }

    /**
     * Result codes for rent/return operations so the UI layer
     * can display the correct message without duplicating logic.
     */
    public enum OperationResult {
        SUCCESS, NOT_FOUND, NOT_AVAILABLE, NOT_RENTED
    }

    public static class RentResult {
        public final OperationResult result;
        public final double cost;

        RentResult(OperationResult result, double cost) {
            this.result = result;
            this.cost = cost;
        }
    }

    public RentResult rentVehicle(String plateNumber, int numberOfDays) {
        Vehicle v = findVehicle(plateNumber);
        if (v == null) {
            return new RentResult(OperationResult.NOT_FOUND, 0);
        }
        if (!v.isAvailable()) {
            return new RentResult(OperationResult.NOT_AVAILABLE, 0);
        }
        double cost = v.calculateRentalCost(numberOfDays);
        v.setAvailable(false);
        return new RentResult(OperationResult.SUCCESS, cost);
    }

    public OperationResult returnVehicle(String plateNumber) {
        Vehicle v = findVehicle(plateNumber);
        if (v == null) {
            return OperationResult.NOT_FOUND;
        }
        if (v.isAvailable()) {
            return OperationResult.NOT_RENTED;
        }
        v.setAvailable(true);
        return OperationResult.SUCCESS;
    }
}
