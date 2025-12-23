package model.game;

import database.GameHistory;
import model.Move;
import model.Player;
import model.board.TicTacToeBoard;
import model.enums.GameState;
import model.enums.Symbol;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


public class ClassicGame implements TicTacToeGame{
    private TicTacToeBoard board;
    private GameState gameState;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    GameHistory gameHistory;
    public ClassicGame(TicTacToeBoard board, Player playerX, Player playerO){
        this.board = board;
        this.playerX= playerX;
        this.playerO = playerO;
        gameState = GameState.X_TURN;
        currentPlayer = playerX;
        gameHistory = new GameHistory();
    }

    public GameMemento save() {
        return new GameMemento(
                board.copyGrid(),
                gameState,
                currentPlayer
        );
    }

    public void restore(GameMemento memento) {
        board.restoreGrid(memento.getBoardState());
        gameState = memento.getGameState();
        currentPlayer = memento.getCurrentPlayer();
    }

    public void start(){
        Scanner sc = new Scanner(System.in);
        while(true){
            board.print();
            System.out.println("1 - make move, 2 - save, 3 - undo ");
            int choice = sc.nextInt();
            if(choice==1){
                Move move = currentPlayer.getPlayerStrategy().makeMove(board);

                if ((gameState == GameState.O_TURN && move.getSymbol()==Symbol.X) ||
                        (gameState == GameState.X_TURN && move.getSymbol()==Symbol.O)){
                    System.out.println("Not your turn");
                    continue;
                }

                if (!board.applyMove(move)){
                    System.out.println("Invalid move");
                    continue;
                }

                if (board.hasWinner()) {
                    System.out.println("Winner: " + currentPlayer.getSymbol());
                    if(currentPlayer.getSymbol()==Symbol.X)gameState = GameState.X_WIN;
                    else gameState = GameState.O_WIN;
                    break;
                }

                if (board.isFull()) {
                    System.out.println("Game Draw");
                    gameState = GameState.DRAW;
                    break;
                }

                currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
                gameState = (gameState==GameState.X_TURN) ? GameState.O_TURN : GameState.X_TURN;
            }
            else if(choice==2){
                gameHistory.save(save());
            }
            else {
                // undo to previously saved state
                restore(gameHistory.undo());
            }
        }
    }
}

