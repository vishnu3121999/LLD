package strategy;

import model.Location;
import model.VehicleFare;
import model.VehicleType;

import java.util.List;
import java.util.Map;

public interface FareStrategy {
    double calculate(Location src, Location dest,VehicleType vehicleType);
}
