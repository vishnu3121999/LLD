import database.DataStore;
import model.*;
import service.Facade;
import strategy.BaseFareStrategy;
import strategy.FareStrategy;
import strategy.SurgeFareStrategy;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Initialize system
        DataStore dataStore = new DataStore();
        FareStrategy fareStrategy = new BaseFareStrategy();
        Facade facade = new Facade(fareStrategy, dataStore);

        System.out.println("========================================");
        System.out.println("   🛺 UBER CAB BOOKING SYSTEM 🛺");
        System.out.println("========================================\n");

        // Register riders
        System.out.println("=== Registering Riders ===");
        String rider1Id = facade.registerRider("Alice");
        String rider2Id = facade.registerRider("Bob");
        System.out.println("Rider 1: Alice (ID: " + rider1Id.substring(0, 8) + "...)");
        System.out.println("Rider 2: Bob (ID: " + rider2Id.substring(0, 8) + "...)\n");

        // Register vehicles (drivers) - Note: vehicles need to be available
        System.out.println("=== Registering Drivers & Vehicles ===");
        String sedan1Id = facade.registerVehicle("John", VehicleType.SEDAN, new Location(0.0, 0.0));
        String sedan2Id = facade.registerVehicle("Mike", VehicleType.SEDAN, new Location(1.0, 1.0));
        String go1Id = facade.registerVehicle("Sarah", VehicleType.GO, new Location(2.0, 2.0));
        String auto1Id = facade.registerVehicle("Raj", VehicleType.AUTO, new Location(3.0, 3.0));
        String bike1Id = facade.registerVehicle("Priya", VehicleType.BIKE, new Location(4.0, 4.0));

        
        System.out.println("Driver 1: John (SEDAN) at (0.0, 0.0)");
        System.out.println("Driver 2: Mike (SEDAN) at (1.0, 1.0)");
        System.out.println("Driver 3: Sarah (GO) at (2.0, 2.0)");
        System.out.println("Driver 4: Raj (AUTO) at (3.0, 3.0)");
        System.out.println("Driver 5: Priya (BIKE) at (4.0, 4.0)\n");

        // Scenario 1: Rider views prices
        System.out.println("=== Scenario 1: Rider views fare estimates ===");
        Location source = new Location(0.5, 0.5);
        Location destination = new Location(5.0, 5.0);
        System.out.println("Source: (" + source.getLatitude() + ", " + source.getLongitude() + ")");
        System.out.println("Destination: (" + destination.getLatitude() + ", " + destination.getLongitude() + ")");
        
        List<VehicleFare> prices = facade.showPrices(source, destination);
        System.out.println("\nAvailable vehicle types and estimated fares:");
        for (VehicleFare vf : prices) {
            System.out.printf("  - %s: ₹%.2f\n", vf.getVehicleType(), vf.getFare());
        }
        System.out.println();

        // Scenario 2: Rider books a ride
        System.out.println("=== Scenario 2: Rider books a ride ===");
        VehicleType selectedType = VehicleType.SEDAN;
        System.out.println("Rider Alice selects: " + selectedType);
        
        Booking booking1 = facade.requestRide(source, destination, selectedType, rider1Id);
        System.out.println("✅ Ride requested!");
        System.out.println("  Booking ID: " + booking1.getId().substring(0, 8) + "...");
        System.out.println("  OTP: " + booking1.getOtp());
        System.out.println("  Status: " + booking1.getBookingStatus());
        System.out.println("  Fare: ₹" + String.format("%.2f", booking1.getFare()));
        System.out.println();

        // Scenario 3: Driver accepts ride
        System.out.println("=== Scenario 3: Driver accepts ride ===");
        // Find available vehicle for this booking
        String vehicleId1 = findAvailableVehicle(dataStore, selectedType, source);
        if (vehicleId1 != null) {
            System.out.println("Driver receives notification and accepts the ride");
            facade.acceptRide(vehicleId1, booking1.getId());
            System.out.println("✅ Driver accepted!");
            System.out.println("  Status: " + booking1.getBookingStatus());
            System.out.println();
        }

        // Scenario 4: OTP verification
        System.out.println("=== Scenario 4: OTP verification ===");
        if (vehicleId1 != null) {
            System.out.println("Driver enters OTP: " + booking1.getOtp());
            boolean otpValid = facade.enterOtp(vehicleId1, booking1.getId(), booking1.getOtp());
            if (otpValid) {
                System.out.println("✅ OTP verified! Ride started.");
                System.out.println("  Status: " + booking1.getBookingStatus());
                System.out.println();
            }
        }

        // Scenario 5: End ride
        System.out.println("=== Scenario 5: End ride ===");
        if (vehicleId1 != null && booking1.getBookingStatus() == BookingStatus.RIDE_STARTED) {
            facade.endRide(booking1.getId());
            System.out.println("✅ Ride completed!");
            System.out.println("  Status: " + booking1.getBookingStatus());
            System.out.println();
        }

        // Scenario 6: Wrong OTP attempts
        System.out.println("=== Scenario 6: Wrong OTP attempts (new booking) ===");
        Location source2 = new Location(1.0, 1.0);
        Booking booking2 = facade.requestRide(source2, new Location(6.0, 6.0), VehicleType.GO, rider2Id);
        System.out.println("Rider Bob requests GO ride. OTP: " + booking2.getOtp());
        
        String vehicleId2 = findAvailableVehicle(dataStore, VehicleType.GO, source2);
        if (vehicleId2 != null) {
            facade.acceptRide(vehicleId2, booking2.getId());
            System.out.println("Driver accepts ride.");
            
            // Try wrong OTP 3 times
            for (int i = 1; i <= 3; i++) {
                System.out.println("Attempt " + i + ": Entering wrong OTP (9999)");
                boolean result = facade.enterOtp(vehicleId2, booking2.getId(), 9999);
                if (!result) {
                    System.out.println("  ❌ Wrong OTP. Failed attempts: " + booking2.getFailedOTPAttempts());
                }
            }
            System.out.println("  Final Status: " + booking2.getBookingStatus());
            System.out.println();
        }

        // Scenario 7: Surge pricing demonstration
        System.out.println("=== Scenario 7: Surge pricing ===");
        DataStore dataStore2 = new DataStore();
        FareStrategy surgeStrategy = new SurgeFareStrategy(15); // High demand
        Facade surgeFacade = new Facade(surgeStrategy, dataStore2);
        
        Location src3 = new Location(0.0, 0.0);
        Location dest3 = new Location(10.0, 10.0);
        List<VehicleFare> surgePrices = surgeFacade.showPrices(src3, dest3);
        
        System.out.println("Fares with surge pricing (high demand - 15 riders):");
        for (VehicleFare vf : surgePrices) {
            System.out.printf("  - %s: ₹%.2f\n", vf.getVehicleType(), vf.getFare());
        }
        System.out.println();

        System.out.println("========================================");
        System.out.println("   SIMULATION COMPLETE ✅");
        System.out.println("========================================");
    }

    // Helper method to find an available vehicle
    private static String findAvailableVehicle(DataStore dataStore, VehicleType vehicleType, Location source) {
        int thresholdDist = 5;
        for (Map.Entry<String, Vehicle> entry : dataStore.getVehicleMap().entrySet()) {
            Vehicle vehicle = entry.getValue();
            if (vehicle.getVehicleType() == vehicleType && 
                vehicle.isAvailable() && 
                vehicle.getLocation().distTo(source) < thresholdDist) {
                return entry.getKey();
            }
        }
        return null;
    }
}
