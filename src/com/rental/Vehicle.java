package com.rental;

/**
 * Abstract base class representing a generic rentable vehicle.
 *
 * ABSTRACTION:
 *   - Vehicle cannot be instantiated directly (it is abstract).
 *   - calculateRentalCost() has no implementation here; every concrete
 *     vehicle type (Car, Motorcycle, Van) must supply its own pricing rule.
 *
 * ENCAPSULATION:
 *   - All fields are private.
 *   - Fields are only reachable through getters and validating setters,
 *     so a Vehicle object can never be created or mutated into an invalid state.
 */
public abstract class Vehicle {

    private String plateNumber;
    private String model;
    private double baseRatePerDay;
    private boolean available;

    public Vehicle(String plateNumber, String model, double baseRatePerDay, boolean available) {
        setPlateNumber(plateNumber);
        setModel(model);
        setBaseRatePerDay(baseRatePerDay);
        this.available = available;
    }

    // ---------- Getters ----------

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRatePerDay() {
        return baseRatePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    // ---------- Setters (with validation) ----------

    public final void setPlateNumber(String plateNumber) {
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Plate number cannot be empty.");
        }
        if (!plateNumber.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Plate number must be alphanumeric.");
        }
        this.plateNumber = plateNumber.toUpperCase();
    }

    public final void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty.");
        }
        this.model = model.trim();
    }

    public final void setBaseRatePerDay(double baseRatePerDay) {
        if (baseRatePerDay <= 0) {
            throw new IllegalArgumentException("Base rate per day must be greater than 0.");
        }
        this.baseRatePerDay = baseRatePerDay;
    }

    public final void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Each vehicle type defines its own cost formula.
     * This is the core abstraction point of the design.
     */
    public abstract double calculateRentalCost(int numberOfDays);

    /**
     * Each vehicle type returns a short label for its type
     * (used in the formatted table display).
     */
    public abstract String getVehicleType();

    /**
     * Each vehicle type describes its own extra/unique field
     * (used in the formatted table display).
     */
    public abstract String getExtraDetail();

    /**
     * Formatted row for the "View All Vehicles" table.
     */
    public String toTableRow() {
        return String.format("%-10s %-10s %-18s %10.2f  %-12s %-20s",
                plateNumber,
                getVehicleType(),
                model,
                baseRatePerDay,
                (available ? "Available" : "Rented"),
                getExtraDetail());
    }
}
