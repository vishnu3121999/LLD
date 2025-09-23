package models.board;

import models.Move;
import models.game.GameStatus;

public class SimpleBoard extends Board{


    public SimpleBoard(int size) {
        super(size);
        boardType = BoardType.SIMPLE_BOARD;

        // init logic
        board = new String[size][size];
    }

    @Override
    public boolean validateAndUpdateBoard(Move move) {
        return true;
    }

    @Override
    public GameStatus checkGameStatus() {
        return null;
    }


}
