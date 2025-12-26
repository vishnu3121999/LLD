# Quick Consistency Rules - Daily Reference

## 🎯 **The 5-Minute Pre-Code Checklist**

Before writing any method that modifies state, ask:

1. **Are all parameters validated?** (null, empty, format)
2. **Does the entity exist?** (check data store)
3. **Is the current state valid?** (state machine rules)
4. **Is this operation thread-safe?** (synchronized if needed)
5. **What happens if it fails?** (rollback plan)

---

## 🔒 **The Golden Rules**

### Rule 1: Validate → Lock → Modify → Unlock
```java
// ✅ ALWAYS follow this order
1. Validate inputs
2. Check entity exists
3. Validate state
4. Acquire lock (synchronized)
5. Double-check inside lock
6. Modify state
7. Release lock
```

### Rule 2: One Entity = One Lock
```java
// ✅ Lock the entity you're modifying
synchronized(booking) {
    // modify booking
}
```

### Rule 3: Never Modify Before Validation
```java
// ❌ BAD
booking.setStatus(NEW_STATUS);  // Modified!
if(invalid) throw exception;   // Too late!

// ✅ GOOD
if(invalid) throw exception;   // Validate first
booking.setStatus(NEW_STATUS); // Then modify
```

### Rule 4: Always Maintain Invariants
```java
// ✅ When you set booking.vehicleId, also set vehicle.isAvailable = false
booking.setVehicleId(vehicleId);
vehicle.setAvailable(false);  // Maintain invariant
```

---

## 📋 **Method Template**

```java
public ReturnType methodName(Params params) {
    // STEP 1: Parameter Validation
    if(params == null || params.isEmpty()) {
        throw new IllegalArgumentException("Invalid params");
    }
    
    // STEP 2: Entity Existence
    Entity entity = dataStore.get(id);
    if(entity == null) {
        throw new IllegalArgumentException("Entity not found");
    }
    
    // STEP 3: State Validation
    if(entity.getStatus() != ExpectedStatus) {
        throw new IllegalStateException("Invalid state");
    }
    
    // STEP 4: Thread-Safe Operation
    synchronized(entity) {
        // STEP 5: Double-Check (inside lock)
        if(entity.getStatus() != ExpectedStatus) {
            throw new IllegalStateException("State changed");
        }
        
        // STEP 6: Perform Operation
        entity.modify();
        
        // STEP 7: Maintain Invariants
        relatedEntity.update();
    }
    
    return result;
}
```

---

## 🚨 **Common Mistakes to Avoid**

| Mistake | Why It's Bad | Fix |
|---------|-------------|-----|
| No null check | `NullPointerException` | Always check first |
| Check outside lock | Race condition | Check inside synchronized block |
| Modify before validate | Inconsistent state | Validate → Modify |
| Forget to release resource | Resource leak | Use try-finally |
| Partial update | Broken invariants | Update all related entities |
| Wrong state transition | Invalid flow | Validate state machine |

---

## ✅ **Quick Validation Checklist**

For every state-changing method:

- [ ] Parameters validated?
- [ ] Entity exists?
- [ ] Current state allows operation?
- [ ] Thread-safe? (synchronized if needed)
- [ ] Invariants maintained?
- [ ] Rollback on failure?
- [ ] Resources released?

---

## 🔄 **State Transition Rules (Uber System)**

```
RIDE_REQUESTED
    ↓ (acceptRide)
DRIVER_ASSIGNED
    ↓ (enterOtp - correct)
RIDE_STARTED
    ↓ (endRide)
RIDE_COMPLETE

RIDE_REQUESTED/DRIVER_ASSIGNED
    ↓ (cancelRide or 3 wrong OTPs)
RIDE_CANCELLED
```

**Rules:**
- Can only accept if `RIDE_REQUESTED`
- Can only enter OTP if `DRIVER_ASSIGNED`
- Can only end if `RIDE_STARTED`
- Cannot cancel if `RIDE_COMPLETE` or `RIDE_CANCELLED`

---

## 🔗 **Invariants to Always Maintain**

1. **Booking-Vehicle:**
   - `booking.vehicleId != null` → `vehicle.isAvailable == false`
   - `booking.status == RIDE_COMPLETE` → `vehicle.isAvailable == true`

2. **OTP Attempts:**
   - `failedOTPAttempts <= 3`
   - `failedOTPAttempts == 3` → `status == RIDE_CANCELLED`

3. **Referential Integrity:**
   - `booking.vehicleId` exists in `vehicleMap`
   - `booking.riderId` exists in `riderMap`

---

## 💡 **Pro Tips**

1. **Fail Fast**: Validate everything upfront, before any state changes
2. **Atomic Operations**: Use synchronized blocks for check-then-act
3. **Double-Check Locking**: Check condition again inside synchronized block
4. **Maintain Invariants**: When you change one thing, update related things
5. **Clear Error Messages**: Tell user exactly what went wrong and why

---

## 🎯 **Remember**

> **"Validate First, Modify Second, Maintain Invariants Always"**


