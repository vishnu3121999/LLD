package models.player;

import models.Move;
import models.board.Board;

public class HumanPlayer extends Player {
    String id;
    String name;
    int matchesPlayed;
    int matchesWon;
    int matchesDrawn;

    public HumanPlayer(String id, String name) {
        super(PlayerType.HUMAN);
        this.id = id;
        this.name = name;
    }

    @Override
    public Move makeMove(Board board) {
        return null;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMatchesDrawn() {
        return matchesDrawn;
    }

    public void setMatchesDrawn(int matchesDrawn) {
        this.matchesDrawn = matchesDrawn;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        this.matchesWon = matchesWon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
