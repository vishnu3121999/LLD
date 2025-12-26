# Multi-Entity Update Guide

## 🎯 **The Problem**
When an operation affects multiple entities, it's easy to forget one. Example:
- Cancel ride → Update booking status AND make vehicle available
- Accept ride → Update booking status AND booking.vehicleId AND vehicle.isAvailable

## ✅ **Solution: Dependency Mapping**

### Step 1: Identify All Affected Entities
Before writing code, list ALL entities that need updating:

```
Operation: cancelRide(bookingId)
Affected Entities:
  1. Booking (status → RIDE_CANCELLED)
  2. Vehicle (isAvailable → true) [if vehicleId exists]
```

### Step 2: Create Update Checklist
Write comments in code listing all updates:

```java
public void cancelRide(String bookingId) {
    Booking booking = getBooking(bookingId);
    
    // UPDATE CHECKLIST:
    // ☐ 1. booking.status = RIDE_CANCELLED
    // ☐ 2. vehicle.isAvailable = true (if vehicleId exists)
    
    booking.setBookingStatus(BookingStatus.RIDE_CANCELLED);
    
    String vehicleId = booking.getVehicleId();
    if(vehicleId != null) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle.setAvailable(true);
    }
}
```

---

## 🔧 **Pattern 1: Update Helper Method**

Create a helper method that handles all related updates:

```java
public class Facade {
    
    // Main operation method - delegates to helper
    public void cancelRide(String bookingId) {
        Booking booking = getBooking(bookingId);
        cancelRideInternal(booking);
    }
    
    // Helper method that does ALL updates
    private void cancelRideInternal(Booking booking) {
        // Update 1: Booking status
        booking.setBookingStatus(BookingStatus.RIDE_CANCELLED);
        
        // Update 2: Vehicle availability
        String vehicleId = booking.getVehicleId();
        if(vehicleId != null) {
            Vehicle vehicle = getVehicle(vehicleId);
            vehicle.setAvailable(true);
        }
        
        // Update 3: Any other related entities...
    }
}
```

---

## 🔧 **Pattern 2: Update Transaction Object**

Create a transaction object that tracks all updates:

```java
public class RideCancellationTransaction {
    private Booking booking;
    private Vehicle vehicle;
    
    public RideCancellationTransaction(Booking booking) {
        this.booking = booking;
        if(booking.getVehicleId() != null) {
            this.vehicle = getVehicle(booking.getVehicleId());
        }
    }
    
    public void execute() {
        // All updates in one place
        booking.setBookingStatus(BookingStatus.RIDE_CANCELLED);
        if(vehicle != null) {
            vehicle.setAvailable(true);
        }
    }
}

// Usage:
public void cancelRide(String bookingId) {
    Booking booking = getBooking(bookingId);
    RideCancellationTransaction transaction = new RideCancellationTransaction(booking);
    transaction.execute();
}
```

---

## 🔧 **Pattern 3: Update Checklist Template**

Create a standard checklist for each operation:

```java
/**
 * Cancels a ride booking.
 * 
 * UPDATES REQUIRED:
 * 1. booking.status → RIDE_CANCELLED
 * 2. vehicle.isAvailable → true (if vehicle assigned)
 * 
 * INVARIANTS MAINTAINED:
 * - Vehicle availability restored
 * - Booking status reflects cancellation
 */
public void cancelRide(String bookingId) {
    // Implementation
}
```

---

## 📋 **Complete Update Map for Uber System**

### Operation: `acceptRide(vehicleId, bookingId)`
**Updates Required:**
1. ✅ `booking.status` → `DRIVER_ASSIGNED`
2. ✅ `booking.vehicleId` → `vehicleId`
3. ✅ `vehicle.isAvailable` → `false`

**Code:**
```java
public void acceptRide(String vehicleId, String bookingId) {
    Booking booking = getBooking(bookingId);
    Vehicle vehicle = getVehicle(vehicleId);
    
    synchronized(booking) {
        // UPDATE 1: Booking status
        booking.setBookingStatus(BookingStatus.DRIVER_ASSIGNED);
        
        // UPDATE 2: Booking vehicle reference
        booking.setVehicleId(vehicleId);
        
        // UPDATE 3: Vehicle availability
        vehicle.setAvailable(false);
    }
}
```

---

### Operation: `cancelRide(bookingId)`
**Updates Required:**
1. ✅ `booking.status` → `RIDE_CANCELLED`
2. ✅ `vehicle.isAvailable` → `true` (if vehicle assigned)

**Code:**
```java
public void cancelRide(String bookingId) {
    Booking booking = getBooking(bookingId);
    
    // UPDATE 1: Booking status
    booking.setBookingStatus(BookingStatus.RIDE_CANCELLED);
    
    // UPDATE 2: Vehicle availability (if assigned)
    String vehicleId = booking.getVehicleId();
    if(vehicleId != null) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle.setAvailable(true);
    }
}
```

---

### Operation: `endRide(bookingId)`
**Updates Required:**
1. ✅ `booking.status` → `RIDE_COMPLETE`
2. ✅ `vehicle.isAvailable` → `true`

**Code:**
```java
public void endRide(String bookingId) {
    Booking booking = getBooking(bookingId);
    
    // UPDATE 1: Booking status
    booking.setBookingStatus(BookingStatus.RIDE_COMPLETE);
    
    // UPDATE 2: Vehicle availability
    String vehicleId = booking.getVehicleId();
    if(vehicleId != null) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle.setAvailable(true);
    }
}
```

---

### Operation: `enterOtp()` - Wrong OTP (3rd attempt)
**Updates Required:**
1. ✅ `booking.failedOTPAttempts` → `+1`
2. ✅ `booking.status` → `RIDE_CANCELLED` (if 3rd attempt)
3. ✅ `vehicle.isAvailable` → `true` (if cancelled)

**Code:**
```java
public boolean enterOtp(String vehicleId, String bookingId, int otp) {
    Booking booking = getBooking(bookingId);
    
    int attempts = booking.getFailedOTPAttempts();
    
    if(booking.getOtp() != otp) {
        // UPDATE 1: Increment failed attempts
        booking.setFailedOTPAttempts(attempts + 1);
        
        // If 3rd failed attempt, cancel ride
        if(attempts + 1 >= 3) {
            // UPDATE 2: Cancel booking
            booking.setBookingStatus(BookingStatus.RIDE_CANCELLED);
            
            // UPDATE 3: Release vehicle
            String vehicleId = booking.getVehicleId();
            if(vehicleId != null) {
                Vehicle vehicle = getVehicle(vehicleId);
                vehicle.setAvailable(true);
            }
        }
        return false;
    }
    
    // Success: UPDATE booking status only
    booking.setBookingStatus(BookingStatus.RIDE_STARTED);
    return true;
}
```

---

## 🎯 **Best Practice: Update Checklist Method**

Add this helper method to track updates:

```java
/**
 * Helper method to ensure all related entities are updated.
 * Call this at the end of each operation to verify.
 */
private void verifyUpdates(String operation, Booking booking) {
    List<String> missingUpdates = new ArrayList<>();
    
    switch(operation) {
        case "acceptRide":
            if(booking.getVehicleId() == null) {
                missingUpdates.add("booking.vehicleId not set");
            }
            if(booking.getBookingStatus() != BookingStatus.DRIVER_ASSIGNED) {
                missingUpdates.add("booking.status not DRIVER_ASSIGNED");
            }
            Vehicle vehicle = getVehicle(booking.getVehicleId());
            if(vehicle != null && vehicle.isAvailable()) {
                missingUpdates.add("vehicle.isAvailable not set to false");
            }
            break;
            
        case "cancelRide":
            if(booking.getBookingStatus() != BookingStatus.RIDE_CANCELLED) {
                missingUpdates.add("booking.status not RIDE_CANCELLED");
            }
            if(booking.getVehicleId() != null) {
                Vehicle v = getVehicle(booking.getVehicleId());
                if(v != null && !v.isAvailable()) {
                    missingUpdates.add("vehicle.isAvailable not set to true");
                }
            }
            break;
    }
    
    if(!missingUpdates.isEmpty()) {
        throw new IllegalStateException("Missing updates: " + missingUpdates);
    }
}
```

---

## 📝 **Template for New Operations**

When adding a new operation, use this template:

```java
/**
 * [Operation Description]
 * 
 * UPDATES REQUIRED:
 * 1. entity1.field1 → value1
 * 2. entity2.field2 → value2
 * 3. entity3.field3 → value3
 * 
 * INVARIANTS MAINTAINED:
 * - Invariant 1
 * - Invariant 2
 * 
 * RELATED ENTITIES:
 * - Entity1 (directly modified)
 * - Entity2 (directly modified)
 * - Entity3 (indirectly affected)
 */
public void operationName(Params params) {
    // Step 1: Get all entities that need updating
    Entity1 entity1 = getEntity1(id1);
    Entity2 entity2 = getEntity2(id2);
    Entity3 entity3 = getEntity3(id3);
    
    // Step 2: Perform all updates
    // UPDATE 1: entity1.field1
    entity1.setField1(value1);
    
    // UPDATE 2: entity2.field2
    entity2.setField2(value2);
    
    // UPDATE 3: entity3.field3
    entity3.setField3(value3);
    
    // Step 3: Verify all updates (optional)
    verifyUpdates("operationName", entity1);
}
```

---

## 🔍 **Quick Checklist Before Committing**

For every operation that modifies state:

- [ ] Listed all affected entities in comments
- [ ] Updated all related entities
- [ ] Maintained all invariants
- [ ] Handled null cases (if entity might not exist)
- [ ] Updated in correct order (if order matters)
- [ ] All updates are atomic (inside synchronized block if needed)

---

## 💡 **Pro Tips**

1. **Write updates as comments first** - List all updates before coding
2. **Group related updates together** - Keep them in same method/block
3. **Use descriptive variable names** - Makes it clear what you're updating
4. **Add verification** - Optional: verify all updates completed
5. **Document in method javadoc** - List all entities affected

---

## 🎯 **Remember**

> **"Every operation that modifies state should have a comment listing ALL entities that need updating"**


