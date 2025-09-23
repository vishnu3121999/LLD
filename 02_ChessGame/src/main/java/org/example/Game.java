package org.example;

import org.example.enums.Status;
import org.example.model.Board;
import org.example.model.Move;
import org.example.players.EasyAIPlayer;
import org.example.players.HumanPlayer;
import org.example.players.Player;
import org.example.strategies.BoardInitStrategy;
import org.example.strategies.Classical;

public class Game  {

    Board board;
    Status status;
    int currTurn;
    Player whitePlayer;
    Player blackPlayer;


    public Game(BoardInitStrategy boardInitStrategy,Player whitePlayer, Player blackPlayer){
        board = Board.getInstance();
        status = Status.INPROGRESS;
        currTurn = 1;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        boardInitStrategy.initBoard(board);
    }

    void start(){
        while (status==Status.INPROGRESS){
            board.displayBoard();
            Move move;
            if(currTurn==1){
                move = whitePlayer.makeMove();
            }
            else move = blackPlayer.makeMove();


        }
    }


}
