package models.board;

import models.Move;
import models.game.GameStatus;

public abstract class Board {
    int size;
    String[][] board;
    BoardType boardType;

    public Board(int size){
        this.size=size;
    }

    public abstract boolean validateAndUpdateBoard(Move move);
    public abstract GameStatus checkGameStatus();
    public String[][] getBoard(){
        return board;
    }
}
