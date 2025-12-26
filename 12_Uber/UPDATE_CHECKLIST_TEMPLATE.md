# Update Checklist Template

## 🎯 **Before Writing Any Method That Modifies State**

### Step 1: List ALL Entities That Need Updating
Write this as a comment at the top of your method:

```java
/**
 * [Method description]
 * 
 * UPDATES REQUIRED (don't forget any!):
 * ☐ 1. entity1.field1 → value1
 * ☐ 2. entity2.field2 → value2
 * ☐ 3. entity3.field3 → value3
 */
```

### Step 2: Mark Each Update in Code
As you write each update, mark it clearly:

```java
// UPDATE 1: entity1.field1
entity1.setField1(value1);

// UPDATE 2: entity2.field2
entity2.setField2(value2);

// UPDATE 3: entity3.field3
entity3.setField3(value3);
```

### Step 3: Verify Before Committing
- [ ] All checkboxes in comment are addressed
- [ ] All updates are in the code
- [ ] Related entities are updated together
- [ ] Invariants are maintained

---

## 📋 **Quick Reference: Your Uber System Updates**

### `acceptRide(vehicleId, bookingId)`
```
☐ booking.status → DRIVER_ASSIGNED
☐ booking.vehicleId → vehicleId
☐ vehicle.isAvailable → false
```

### `cancelRide(bookingId)`
```
☐ booking.status → RIDE_CANCELLED
☐ vehicle.isAvailable → true (if vehicle assigned)
```

### `endRide(bookingId)`
```
☐ booking.status → RIDE_COMPLETE
☐ vehicle.isAvailable → true (if vehicle assigned)
```

### `enterOtp()` - Wrong OTP (3rd attempt)
```
☐ booking.failedOTPAttempts → +1
☐ booking.status → RIDE_CANCELLED (via cancelRide)
☐ vehicle.isAvailable → true (via cancelRide)
```

### `enterOtp()` - Correct OTP
```
☐ booking.status → RIDE_STARTED
```

---

## 💡 **Pro Tip**

**Always ask yourself:**
> "If I update this entity, what other entities are affected?"

Example:
- Update `booking.status` → Does vehicle need updating? ✅ Yes (availability)
- Update `booking.vehicleId` → Does vehicle need updating? ✅ Yes (availability)
- Update `vehicle.isAvailable` → Does booking need updating? ❌ No (but check invariants)

---

## 🎯 **Remember**

> **"One operation, multiple updates - list them all before coding!"**


