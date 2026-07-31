public class Motorcycle extends Vehicle {

    private int engineDisplacementCc;

    public Motorcycle(String plateNumber, String model, double baseRatePerDay, boolean available, int engineDisplacementCc) {
        super(plateNumber, model, baseRatePerDay, available);
        setEngineDisplacementCc(engineDisplacementCc);
    }

    public int getEngineDisplacementCc() {
        return engineDisplacementCc;
    }

    public final void setEngineDisplacementCc(int engineDisplacementCc) {
        if (engineDisplacementCc <= 0) {
            throw new IllegalArgumentException("Engine displacement must be greater than 0.");
        }
        this.engineDisplacementCc = engineDisplacementCc;
    }

    @Override
    public double calculateRentalCost(int numberOfDays) {
        return getBaseRatePerDay() * numberOfDays;
    }

    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }

    @Override
    public String getExtraDetail() {
        return "Engine: " + engineDisplacementCc + "cc";
    }
}
