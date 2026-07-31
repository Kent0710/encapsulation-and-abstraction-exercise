package com.rental;

public class Car extends Vehicle {

    private int numberOfSeats;

    public Car(String plateNumber, String model, double baseRatePerDay, boolean available, int numberOfSeats) {
        super(plateNumber, model, baseRatePerDay, available);
        setNumberOfSeats(numberOfSeats);
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public final void setNumberOfSeats(int numberOfSeats) {
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats must be greater than 0.");
        } 
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public double calculateRentalCost(int numberOfDays) {
        return getBaseRatePerDay() * numberOfDays;
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public String getExtraDetail() {
        return "Seats: " + numberOfSeats;
    }
}
