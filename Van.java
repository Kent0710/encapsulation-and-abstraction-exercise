public class Van extends Vehicle {

    private static final double DRIVER_FEE = 500.0;

    private int cargoCapacityKg;

    public Van(String plateNumber, String model, double baseRatePerDay,
               boolean available, int cargoCapacityKg) {
        super(plateNumber, model, baseRatePerDay, available);
        setCargoCapacityKg(cargoCapacityKg);
    }

    public int getCargoCapacityKg() {
        return cargoCapacityKg;
    }

    public final void setCargoCapacityKg(int cargoCapacityKg) {
        if (cargoCapacityKg <= 0) {
            throw new IllegalArgumentException("Cargo capacity must be greater than 0.");
        }
        this.cargoCapacityKg = cargoCapacityKg;
    }

    @Override
    public double calculateRentalCost(int numberOfDays) {
        return (getBaseRatePerDay() * numberOfDays) + DRIVER_FEE;
    }

    @Override
    public String getVehicleType() {
        return "Van";
    }

    @Override
    public String getExtraDetail() {
        return "Cargo: " + cargoCapacityKg + "kg (+Php500 driver fee)";
    }
}
