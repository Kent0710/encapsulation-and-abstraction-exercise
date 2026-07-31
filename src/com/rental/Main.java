package com.rental;

import java.util.Scanner;

/**
 * Console entry point. Handles menu I/O only; all business rules
 * (validation, duplicate checks, rent/return logic) live in
 * RentalSystem and the Vehicle hierarchy.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final RentalSystem system = new RentalSystem();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addVehicle();
                    break;
                case "2":
                    viewAllVehicles();
                    break;
                case "3":
                    rentVehicle();
                    break;
                case "4":
                    returnVehicle();
                    break;
                case "5":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== VEHICLE RENTAL SYSTEM =====");
        System.out.println("1. Add Vehicle");
        System.out.println("2. View All Vehicles");
        System.out.println("3. Rent a Vehicle");
        System.out.println("4. Return a Vehicle");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    // ---------- 1. Add Vehicle ----------

    private static void addVehicle() {
        System.out.println("\nSelect vehicle type:");
        System.out.println("1. Car");
        System.out.println("2. Motorcycle");
        System.out.println("3. Van");
        System.out.print("Choice: ");
        String typeChoice = scanner.nextLine().trim();

        if (!typeChoice.equals("1") && !typeChoice.equals("2") && !typeChoice.equals("3")) {
            System.out.println("Invalid vehicle type.");
            return;
        }

        try {
            String plateNumber = readNonEmptyLine("Enter Plate Number: ");
            if (system.isDuplicatePlate(plateNumber)) {
                System.out.println("Error: Plate number already exists!");
                return;
            }

            String model = readNonEmptyLine("Enter Model: ");
            double baseRate = readPositiveDouble("Enter Base Rate per Day (Php): ");

            Vehicle vehicle;
            switch (typeChoice) {
                case "1": {
                    int seats = readPositiveInt("Enter Number of Seats: ");
                    vehicle = new Car(plateNumber, model, baseRate, true, seats);
                    break;
                }
                case "2": {
                    int cc = readPositiveInt("Enter Engine Displacement (cc): ");
                    vehicle = new Motorcycle(plateNumber, model, baseRate, true, cc);
                    break;
                }
                default: {
                    int cargo = readPositiveInt("Enter Cargo Capacity (kg): ");
                    vehicle = new Van(plateNumber, model, baseRate, true, cargo);
                    break;
                }
            }

            boolean added = system.addVehicle(vehicle);
            if (added) {
                System.out.println("Vehicle added successfully!");
            } else {
                System.out.println("Error: Plate number already exists!");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---------- 2. View All Vehicles ----------

    private static void viewAllVehicles() {
        if (system.isEmpty()) {
            System.out.println("\nNo vehicles registered yet.");
            return;
        }

        System.out.println();
        System.out.printf("%-10s %-10s %-18s %10s  %-12s %-20s%n",
                "PLATE", "TYPE", "MODEL", "RATE/DAY", "STATUS", "DETAILS");
        System.out.println("-".repeat(90));

        for (Vehicle v : system.getAllVehicles()) {
            System.out.println(v.toTableRow());
        }
    }

    // ---------- 3. Rent a Vehicle ----------

    private static void rentVehicle() {
        String plateNumber = readNonEmptyLine("Enter Plate Number: ");
        int days = readPositiveInt("Enter number of rental days: ");

        RentalSystem.RentResult result = system.rentVehicle(plateNumber, days);
        switch (result.result) {
            case SUCCESS:
                System.out.printf("Rental confirmed! Total cost: Php%.2f%n", result.cost);
                break;
            case NOT_AVAILABLE:
                System.out.println("Vehicle not available!");
                break;
            case NOT_FOUND:
                System.out.println("Vehicle not found!");
                break;
            default:
                break;
        }
    }

    // ---------- 4. Return a Vehicle ----------

    private static void returnVehicle() {
        String plateNumber = readNonEmptyLine("Enter Plate Number: ");

        RentalSystem.OperationResult result = system.returnVehicle(plateNumber);
        switch (result) {
            case SUCCESS:
                System.out.println("Vehicle returned successfully!");
                break;
            case NOT_RENTED:
                System.out.println("Vehicle was not rented.");
                break;
            case NOT_FOUND:
                System.out.println("Vehicle not found!");
                break;
            default:
                break;
        }
    }

    // ---------- Input helpers ----------

    private static String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty. Try again.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
