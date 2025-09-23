package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.SlotStatus;
import org.example.enums.Specialization;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Slot {
    String id;
    String doctorId;
    LocalDateTime startTime;
    SlotStatus slotStatus;
    Specialization specialization;
}
