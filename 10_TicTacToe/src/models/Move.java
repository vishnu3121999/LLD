package models;

public class Move {
    int x;
    int y;
    String playerId;

    public Move( int x, int y,String playerId) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }
}
