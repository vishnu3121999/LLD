package models.player;

import models.Move;
import models.board.Board;


// Can make it singleton
public class Engine {
    static Move getBestMove(int depth, Board board,String id){
        int size = board.getBoard().length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board.getBoard()[i][j]==null)return new Move(i,j,id);
            }
        }
        return null;
    }
}
