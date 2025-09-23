package org.example.model;

import org.example.enums.PieceType;

public abstract class Piece {
    int color;
    PieceType pieceType;
    abstract boolean isValid(Board board, Move move);
}
