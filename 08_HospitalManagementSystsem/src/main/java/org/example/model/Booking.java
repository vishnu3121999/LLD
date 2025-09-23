package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Booking {
    String id;
    String patientId;
    String slotId;
}
