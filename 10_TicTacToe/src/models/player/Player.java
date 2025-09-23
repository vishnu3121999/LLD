package models.player;

import models.Move;
import models.board.Board;

public abstract class Player {
    PlayerType playerType;
    public abstract Move makeMove(Board board);


    public Player(PlayerType playerType) {
        this.playerType = playerType;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}
