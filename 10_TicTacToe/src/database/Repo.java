package database;

import models.Move;
import models.game.Game;
import models.player.Player;

import java.util.HashMap;
import java.util.Map;

public class Repo {
    Map<String, Player> playerMap;
    Map<String, Game> gameMap;
    Map<String, Move> moveMap;

    public Repo(){
        playerMap = new HashMap<>();
        gameMap = new HashMap<>();
        moveMap = new HashMap<>();
    }

    public Map<String, Game> getGameMap() {
        return gameMap;
    }

    public void setGameMap(Map<String, Game> gameMap) {
        this.gameMap = gameMap;
    }

    public Map<String, Move> getMoveMap() {
        return moveMap;
    }

    public void setMoveMap(Map<String, Move> moveMap) {
        this.moveMap = moveMap;
    }

    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
    }
}
