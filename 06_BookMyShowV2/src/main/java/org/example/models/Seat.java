package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.SeatStatus;

@Data
@AllArgsConstructor
public class Seat {
    String id;
    SeatStatus seatStatus;
}
