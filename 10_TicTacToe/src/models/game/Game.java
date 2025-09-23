package models.game;

import models.board.Board;
import models.player.Player;

import java.util.List;

public abstract class Game {
    String id;
    int noOfPlayers;
    List<Player> players;
    GameType gameType;
    int currentTurn;
    GameStatus gameStatus;
    int winner;
    Board board;

    public Game(String id, int noOfPlayers, List<Player> players, Board board) {
        this.id = id;
        this.noOfPlayers = noOfPlayers;
        this.players = players;
        this.board = board;
        currentTurn = 0;
        winner = -1;
        gameStatus = GameStatus.WAITING_FOR_PLAYERS;
    }

    public abstract void start();


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
