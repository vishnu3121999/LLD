package model.game;

public interface TicTacToeGame {
    public void start();
    public GameMemento save();
    public void restore(GameMemento memento);
}

