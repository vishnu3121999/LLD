package strategy;

import model.Move;
import model.board.TicTacToeBoard;
import model.enums.Symbol;

import java.util.Scanner;

public class HumanPlayerStrategy implements PlayerStrategy {

    public Move makeMove(TicTacToeBoard board) {
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();
        String symbol = sc.next();
        return new Move(row,col,(symbol.equals("X"))?Symbol.X:Symbol.O);
    }
}


