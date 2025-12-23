package model.board;

import model.Move;
import model.enums.Symbol;

public interface TicTacToeBoard {
    public boolean applyMove(Move move);
    public boolean hasWinner();
    public boolean isFull();
    public void print();
    Symbol[][] copyGrid();
    void restoreGrid(Symbol[][] boardState);
}

