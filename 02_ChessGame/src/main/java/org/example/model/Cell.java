package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell {
    int x;
    int y;
    int color;
    Piece piece;
}
