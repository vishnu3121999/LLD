package service;

import database.DataStore;
import model.*;
import strategy.FareStrategy;

import java.util.*;

public class Facade {

    private FareStrategy fareStrategy;
    private DataStore dataStore;
    private Random random = new Random();
    public Facade(FareStrategy fareStrategy,DataStore dataStore){
        this.fareStrategy = fareStrategy;
        this.dataStore = dataStore;
    }

    public String registerRider(String name){
        String id = getRandomId();
        Rider rider = new Rider(id,name);
        dataStore.putRider(id,rider);
        return id;
    }

    public String registerVehicle(String name,VehicleType vehicleType, Location currLocation){
        String id = getRandomId();
        Vehicle vehicle = new Vehicle(id,name,vehicleType,currLocation,true);
        dataStore.putVehicle(id,vehicle);
        return id;
    }

    public List<VehicleFare> showPrices(Location source, Location destination){
        List<VehicleFare> estimates = new ArrayList<>();
        for (VehicleType type : VehicleType.values()) {
            double fare = fareStrategy.calculate(source, destination, type);
            fare = Math.round(fare * 100.0) / 100.0;
            estimates.add(new VehicleFare(type, fare));
        }
        return estimates;
    }

    public Booking requestRide(Location source, Location destination, VehicleType vehicleType,String riderId){
        double fare =  fareStrategy.calculate(source,destination,vehicleType);
        String bookingId = getRandomId();
        int otp = random.nextInt(9999-1000)+1000;
        Booking booking = new Booking(otp,bookingId,riderId,null,source,destination, BookingStatus.RIDE_REQUESTED,fare);
        dataStore.putBooking(bookingId, booking);
        List<Vehicle> nearByVehicles = findNearByVehicles(source,vehicleType);
        notifyDrivers(nearByVehicles,source,fare);
        return booking;
    }

    public void acceptRide(String vehicleId, String bookingId){
        var booking = dataStore.getBooking(bookingId);
        if(booking.getVehicleId()!=null)return;
        booking.setBookingStatus(BookingStatus.DRIVER_ASSIGNED);
        booking.setVehicleId(vehicleId);
        var vehicle = dataStore.getVehicle(vehicleId);
        vehicle.setAvailable(false);
    }

    public boolean enterOtp(String vehicleId, String bookingId, int otp){
        var booking = dataStore.getBooking(bookingId);
        int attempts = booking.getFailedOTPAttempts();
        if(attempts==3){
            cancelRide(bookingId);
            return false;
        }
        if(booking.getOtp()!=otp){
            booking.setFailedOTPAttempts(attempts+1);
            return false;
        }
        booking.setBookingStatus(BookingStatus.RIDE_STARTED);
        return true;
    }

    public void endRide(String bookingId){
        var booking = dataStore.getBooking(bookingId);
        booking.setBookingStatus(BookingStatus.RIDE_COMPLETE);
        String vehicleid = booking.getVehicleId();
        var vehicle = dataStore.getVehicle(vehicleid);
        vehicle.setAvailable(true);
    }

    public void cancelRide(String bookingId) {
        var booking = dataStore.getBooking(bookingId);
        booking.setBookingStatus(BookingStatus.RIDE_CANCELLED);
        String vehicleid = booking.getVehicleId();
        var vehicle = dataStore.getVehicle(vehicleid);
        vehicle.setAvailable(true);
    }

    private List<Vehicle> findNearByVehicles(Location source, VehicleType vehicleType) {
        ArrayList<Vehicle> list = new ArrayList<>();
        int threshouldDist = 5;
        for(var vehicle:dataStore.getVehicleMap().values()){
            if(vehicle.getVehicleType()==vehicleType && vehicle.isAvailable()
                    && vehicle.getLocation().distTo(source)<threshouldDist){
                list.add(vehicle);
            }
        }
        return list;
    }

    private void notifyDrivers(List<Vehicle> nearByVehicles, Location source, double fare) {
        // send notification
    }

    String getRandomId(){
        return UUID.randomUUID().toString();
    }

}
