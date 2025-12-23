package model.game;

import model.Player;
import model.enums.GameState;
import model.enums.Symbol;

public final class GameMemento {
    private final Symbol[][] boardState;
    private final GameState gameState;
    private final Player currentPlayer;

    GameMemento(Symbol[][] boardState, GameState gameState, Player currentPlayer) {
        this.boardState = boardState;
        this.gameState = gameState;
        this.currentPlayer = currentPlayer;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    Symbol[][] getBoardState() {
        return boardState;
    }

    GameState getGameState() {
        return gameState;
    }
}


