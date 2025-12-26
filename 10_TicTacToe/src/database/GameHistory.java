package database;

import model.game.GameMemento;
import model.game.TicTacToeGame;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameHistory {
    private final Deque<GameMemento> history = new ArrayDeque<>();

    public void save(GameMemento memento) {
        history.push(memento);
    }

    public GameMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
}


