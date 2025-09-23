package models.player;

import models.Move;
import models.board.Board;

public class AiPlayer extends Player{

    String id;
    int difficulty;

    public AiPlayer(int difficulty) {
        super(PlayerType.AI);
        this.difficulty = difficulty;
        id = "AiPlayer-"+difficulty;
    }

    @Override
    public Move makeMove(Board board) {
        return Engine.getBestMove(difficulty,board,id);
    }
}
