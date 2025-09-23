public abstract class Vehicle {
    String license;
    VehicleType vehicleType;

    public Vehicle(VehicleType vehicleType, String license) {
        this.vehicleType = vehicleType;
        this.license = license;
    }
}

