# System Consistency Checklist

## 🔒 **1. Atomic Operations (All-or-Nothing)**

### ✅ Rules:
- [ ] **Multi-step operations must be atomic** - If operation fails midway, rollback all changes
- [ ] **Use synchronized blocks/locks** for critical sections that modify shared state
- [ ] **Check-then-act operations** must be inside synchronized blocks
- [ ] **Never modify state before validation** - Validate first, then modify

### 📝 Examples:
```java
// ❌ BAD: Not atomic
booking.setVehicleId(vehicleId);
vehicle.setAvailable(false);  // If this fails, booking is inconsistent

// ✅ GOOD: Atomic with validation
synchronized(booking) {
    if(booking.getVehicleId() != null) return;  // Check
    booking.setVehicleId(vehicleId);            // Act
    vehicle.setAvailable(false);
}
```

---

## 🔄 **2. State Transition Validation**

### ✅ Rules:
- [ ] **Define valid state transitions** - Document which states can transition to which
- [ ] **Validate current state** before allowing transition
- [ ] **Reject invalid transitions** with clear error messages
- [ ] **Never skip states** - Follow the defined flow

### 📝 State Transition Matrix:
```
RIDE_REQUESTED → DRIVER_ASSIGNED → RIDE_STARTED → RIDE_COMPLETE
                ↓
            RIDE_CANCELLED (can cancel from REQUESTED or ASSIGNED)
```

### 📝 Example:
```java
// ✅ GOOD: Validate state before transition
if(booking.getBookingStatus() != BookingStatus.DRIVER_ASSIGNED) {
    throw new IllegalStateException("Invalid state transition");
}
booking.setBookingStatus(BookingStatus.RIDE_STARTED);
```

---

## 🔗 **3. Resource Availability & Locking**

### ✅ Rules:
- [ ] **Check resource availability** before acquiring it
- [ ] **Lock resource immediately** when acquired
- [ ] **Release resource** in finally block or on all exit paths
- [ ] **Verify resource still available** inside synchronized block (double-check pattern)

### 📝 Example:
```java
// ✅ GOOD: Double-check locking pattern
synchronized(booking) {
    if(!vehicle.isAvailable()) {  // Check again inside lock
        throw new IllegalStateException("Vehicle no longer available");
    }
    vehicle.setAvailable(false);  // Lock it
}
```

---

## 🛡️ **4. Null Safety & Existence Checks**

### ✅ Rules:
- [ ] **Check for null** before dereferencing
- [ ] **Verify entity exists** in data store before operations
- [ ] **Validate all required parameters** at method entry
- [ ] **Handle null gracefully** - either throw exception or return early

### 📝 Checklist:
```java
// ✅ GOOD: Comprehensive null checks
public void acceptRide(String vehicleId, String bookingId) {
    // 1. Parameter validation
    if(vehicleId == null || bookingId == null) {
        throw new IllegalArgumentException("Parameters cannot be null");
    }
    
    // 2. Entity existence check
    Booking booking = dataStore.getBookingMap().get(bookingId);
    if(booking == null) {
        throw new IllegalArgumentException("Booking not found");
    }
    
    Vehicle vehicle = dataStore.getVehicleMap().get(vehicleId);
    if(vehicle == null) {
        throw new IllegalArgumentException("Vehicle not found");
    }
    
    // 3. Proceed with operation
}
```

---

## 🔄 **5. Rollback & Cleanup**

### ✅ Rules:
- [ ] **Identify rollback points** - Know what to undo if operation fails
- [ ] **Use try-finally** for cleanup operations
- [ ] **Restore previous state** on failure
- [ ] **Release locks/resources** even on exceptions

### 📝 Example:
```java
// ✅ GOOD: Rollback on failure
public void acceptRide(String vehicleId, String bookingId) {
    Booking booking = getBooking(bookingId);
    Vehicle vehicle = getVehicle(vehicleId);
    
    synchronized(booking) {
        try {
            booking.setVehicleId(vehicleId);
            vehicle.setAvailable(false);
            booking.setBookingStatus(BookingStatus.DRIVER_ASSIGNED);
        } catch (Exception e) {
            // Rollback
            booking.setVehicleId(null);
            vehicle.setAvailable(true);
            throw e;
        }
    }
}
```

---

## 📊 **6. Data Invariants**

### ✅ Rules:
- [ ] **Define system invariants** - Conditions that must always be true
- [ ] **Validate invariants** before and after operations
- [ ] **Maintain referential integrity** - Foreign keys must exist
- [ ] **Keep counts consistent** - If you add to one place, remove from another

### 📝 Invariants for Uber System:
```java
// Invariant 1: If vehicle is assigned to booking, vehicle.isAvailable == false
// Invariant 2: If booking has vehicleId, booking.status != RIDE_REQUESTED
// Invariant 3: If booking.status == RIDE_COMPLETE, vehicle.isAvailable == true
// Invariant 4: failedOTPAttempts <= 3
// Invariant 5: If booking.vehicleId != null, vehicle must exist in dataStore
```

### 📝 Example:
```java
// ✅ GOOD: Maintain invariant
public void endRide(String bookingId) {
    Booking booking = getBooking(bookingId);
    String vehicleId = booking.getVehicleId();
    
    booking.setBookingStatus(BookingStatus.RIDE_COMPLETE);
    
    // Maintain invariant: vehicle must be available after ride ends
    if(vehicleId != null) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle.setAvailable(true);  // Restore invariant
    }
}
```

---

## 🔐 **7. Concurrency Safety**

### ✅ Rules:
- [ ] **Identify shared mutable state** - What can be modified by multiple threads?
- [ ] **Use appropriate synchronization** - synchronized, locks, or atomic operations
- [ ] **Minimize lock scope** - Hold locks for shortest time possible
- [ ] **Avoid deadlocks** - Always acquire locks in same order
- [ ] **Use thread-safe collections** - ConcurrentHashMap, etc.

### 📝 Example:
```java
// ✅ GOOD: Thread-safe with minimal lock scope
public void acceptRide(String vehicleId, String bookingId) {
    Booking booking = getBooking(bookingId);
    
    // Lock only the booking object, not entire method
    synchronized(booking) {
        // Critical section - minimal code
        if(booking.getVehicleId() != null) return;
        booking.setVehicleId(vehicleId);
        getVehicle(vehicleId).setAvailable(false);
    }
    // Lock released here
}
```

---

## ✅ **8. Validation Order**

### ✅ Rules:
- [ ] **Validate in order: Parameters → Existence → State → Business Rules**
- [ ] **Fail fast** - Validate early, before any state changes
- [ ] **Validate all preconditions** before operation
- [ ] **Validate postconditions** after operation (optional, for testing)

### 📝 Validation Order Template:
```java
public void someOperation(String id, String otherId) {
    // 1. Parameter validation
    if(id == null || otherId == null) {
        throw new IllegalArgumentException("Parameters cannot be null");
    }
    
    // 2. Entity existence
    Entity entity = dataStore.get(id);
    if(entity == null) {
        throw new IllegalArgumentException("Entity not found");
    }
    
    // 3. State validation
    if(entity.getStatus() != ExpectedStatus.READY) {
        throw new IllegalStateException("Invalid state");
    }
    
    // 4. Business rule validation
    if(!entity.canPerformOperation()) {
        throw new IllegalStateException("Business rule violation");
    }
    
    // 5. NOW perform operation
    entity.performOperation();
}
```

---

## 🔍 **9. Consistency Checks (Post-Operation)**

### ✅ Rules:
- [ ] **Verify operation succeeded** - Check return values, exceptions
- [ ] **Validate state after operation** - Ensure invariants still hold
- [ ] **Log state changes** - For debugging and audit
- [ ] **Handle partial failures** - What if only part of operation succeeds?

### 📝 Example:
```java
// ✅ GOOD: Verify after operation
public void acceptRide(String vehicleId, String bookingId) {
    // ... perform operation ...
    
    // Verify consistency
    Booking booking = getBooking(bookingId);
    Vehicle vehicle = getVehicle(vehicleId);
    
    assert booking.getVehicleId().equals(vehicleId);
    assert !vehicle.isAvailable();
    assert booking.getBookingStatus() == BookingStatus.DRIVER_ASSIGNED;
}
```

---

## 📋 **10. Pre-Operation Checklist Template**

Use this checklist before implementing any state-changing operation:

```java
public void someOperation(/* params */) {
    // ☐ 1. Validate all parameters (null, empty, format)
    // ☐ 2. Check entity existence in data store
    // ☐ 3. Validate current state allows operation
    // ☐ 4. Check business rules/preconditions
    // ☐ 5. Acquire necessary locks (synchronized blocks)
    // ☐ 6. Double-check conditions inside lock (double-check locking)
    // ☐ 7. Perform operation atomically
    // ☐ 8. Maintain all invariants
    // ☐ 9. Release locks/resources
    // ☐ 10. Handle exceptions and rollback if needed
}
```

---

## 🎯 **Quick Reference: Common Patterns**

### Pattern 1: Check-Then-Act (Thread-Safe)
```java
synchronized(resource) {
    if(condition) {
        performAction();
    }
}
```

### Pattern 2: Try-Finally Cleanup
```java
try {
    acquireResource();
    performOperation();
} finally {
    releaseResource();  // Always executes
}
```

### Pattern 3: Transaction-like (Rollback on Error)
```java
State previousState = saveState();
try {
    performOperation();
} catch (Exception e) {
    restoreState(previousState);  // Rollback
    throw e;
}
```

### Pattern 4: State Machine Validation
```java
if(!isValidTransition(currentState, newState)) {
    throw new IllegalStateException("Invalid transition");
}
currentState = newState;
```

---

## 🚨 **Red Flags (Things to Avoid)**

- ❌ **Modifying state before validation**
- ❌ **Not checking null before dereferencing**
- ❌ **Not releasing resources in finally blocks**
- ❌ **Allowing invalid state transitions**
- ❌ **Race conditions in check-then-act operations**
- ❌ **Not maintaining referential integrity**
- ❌ **Partial updates without rollback**
- ❌ **Ignoring exceptions without cleanup**

---

## 📝 **For Your Uber System Specifically**

### Critical Consistency Points:

1. **Booking-Vehicle Relationship:**
   - ☐ If `booking.vehicleId != null`, then `vehicle.isAvailable == false`
   - ☐ If `booking.status == RIDE_COMPLETE`, then `vehicle.isAvailable == true`

2. **State Transitions:**
   - ☐ Only `RIDE_REQUESTED` → `DRIVER_ASSIGNED` → `RIDE_STARTED` → `RIDE_COMPLETE`
   - ☐ Can cancel from `RIDE_REQUESTED` or `DRIVER_ASSIGNED` only

3. **OTP Attempts:**
   - ☐ `failedOTPAttempts` must be <= 3
   - ☐ If `failedOTPAttempts == 3`, booking must be cancelled

4. **Vehicle Availability:**
   - ☐ Vehicle can only be assigned if `isAvailable == true`
   - ☐ When assigned, immediately set `isAvailable == false`
   - ☐ When ride ends/cancels, set `isAvailable == true`

---

## ✅ **Final Checklist Before Committing Code**

- [ ] All null checks in place
- [ ] All state validations in place
- [ ] Thread-safety verified for shared state
- [ ] All invariants maintained
- [ ] Rollback logic for failures
- [ ] Resources released in finally blocks
- [ ] Error messages are clear
- [ ] No race conditions
- [ ] All edge cases handled


