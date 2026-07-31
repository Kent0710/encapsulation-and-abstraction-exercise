# Vehicle Rental System

A simple console-based Vehicle Rental System in Java demonstrating **abstraction**
and **encapsulation**.

## How it demonstrates OOP principles

**Abstraction**
- `Vehicle` is an `abstract class`. It can never be instantiated on its own —
  only concrete types (`Car`, `Motorcycle`, `Van`) can be created.
- `calculateRentalCost(int numberOfDays)` is declared abstract in `Vehicle`.
  Each subclass supplies its own pricing rule (Car/Motorcycle: base rate × days;
  Van: base rate × days + Php 500 driver fee). Calling code just calls
  `vehicle.calculateRentalCost(days)` without needing to know or check which
  subclass it is.
- `RentalSystem` exposes only what the menu needs (`addVehicle`, `findVehicle`,
  `rentVehicle`, `returnVehicle`) and hides how vehicles are stored internally.

**Encapsulation**
- All fields in `Vehicle` and its subclasses are `private`.
- Fields are only reachable through getters and validating setters — invalid
  data (empty plate number, non-alphanumeric plate, zero/negative rate,
  zero/negative seats, etc.) can never be assigned.
- The vehicle collection inside `RentalSystem` (a `Map<String, Vehicle>`) is
  `private`; outside code cannot bypass it to add a duplicate plate number.

## Project structure

```
src/com/rental/
├── Vehicle.java        # abstract base class (shared fields + abstract cost method)
├── Car.java             # Car-specific field (seats) + cost rule
├── Motorcycle.java       # Motorcycle-specific field (engine cc) + cost rule
├── Van.java              # Van-specific field (cargo kg) + cost rule + driver fee
├── RentalSystem.java     # encapsulated vehicle storage + rent/return business logic
└── Main.java             # console menu (I/O only)
```

## How to run

Requires JDK 8+.

```bash
# From the project root:
javac -d out src/com/rental/*.java
java -cp out com.rental.Main
```

## Menu

```
===== VEHICLE RENTAL SYSTEM =====
1. Add Vehicle
2. View All Vehicles
3. Rent a Vehicle
4. Return a Vehicle
5. Exit
```

1. **Add Vehicle** — choose Car/Motorcycle/Van, enter details. Rejects empty
   fields, non-positive numbers, and duplicate plate numbers.
2. **View All Vehicles** — formatted table with plate, type, model, rate,
   availability, and the type-specific detail.
3. **Rent a Vehicle** — enter plate + days. Computes cost using that
   vehicle's own rule, marks it unavailable. Handles "not found" and
   "not available".
4. **Return a Vehicle** — enter plate. Marks it available again. Handles
   "not found" and "was not rented".
5. **Exit**

## Sample cost calculation

| Type       | Rate/Day | Days | Extra          | Total Cost                  |
|------------|----------|------|----------------|------------------------------|
| Car        | 1500     | 3    | —              | 1500 × 3 = **4500**          |
| Motorcycle | 500      | 2    | —              | 500 × 2 = **1000**           |
| Van        | 3000     | 2    | +500 driver fee| (3000 × 2) + 500 = **6500**  |
