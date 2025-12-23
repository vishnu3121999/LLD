import strategy.HumanPlayerStrategy;
import model.Player;
import model.board.ClassicBoard;
import model.board.TicTacToeBoard;
import model.enums.Symbol;
import model.game.ClassicGame;
import model.game.TicTacToeGame;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player(Symbol.X, new HumanPlayerStrategy());
        Player player2 = new Player(Symbol.O, new HumanPlayerStrategy());

        TicTacToeBoard board = new ClassicBoard(3);
        TicTacToeGame game = new ClassicGame(board,player1,player2);

        game.start();
    }
}

