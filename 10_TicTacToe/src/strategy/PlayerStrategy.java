package strategy;

import model.Move;
import model.board.TicTacToeBoard;

public interface PlayerStrategy {
    public Move makeMove(TicTacToeBoard board);
}


