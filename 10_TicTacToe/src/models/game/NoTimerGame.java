package models.game;

import models.Move;
import models.board.Board;
import models.player.HumanPlayer;
import models.player.Player;
import models.player.PlayerType;

import java.util.List;

public class NoTimerGame extends Game{

    public NoTimerGame(String id, int noOfPlayers, List<Player> players, Board board) {
        super(id, noOfPlayers, players,board);
        gameType = GameType.NO_TIMER_GAME;
    }

    @Override
    public void start() {
        gameStatus = GameStatus.IN_PROGRESS;
        while (gameStatus==GameStatus.IN_PROGRESS){
            Player currPlayer = players.get(currentTurn);
            Move move = currPlayer.makeMove(board);
            boolean valid = board.validateAndUpdateBoard(move);
            if(!valid){
                throw new IllegalStateException("Invalid Move");
            }
            else{
                gameStatus = board.checkGameStatus();
                currentTurn = (currentTurn+1)%noOfPlayers;

            }
        }
        if(gameStatus==GameStatus.RESULT){
            winner = (noOfPlayers+currentTurn-1)%noOfPlayers;
        }
        doPostGameUpdates(winner);
    }


    private void doPostGameUpdates(int winner) {
        if(winner==-1){
            for(var player:players){
                if(player.getPlayerType()==PlayerType.HUMAN){
                    ((HumanPlayer) player).setMatchesPlayed(((HumanPlayer) player).getMatchesPlayed()+1);
                    ((HumanPlayer) player).setMatchesDrawn(((HumanPlayer) player).getMatchesDrawn()+1);
                }
            }
            return;
        }
        for(var player:players){
            if(player.getPlayerType()==PlayerType.HUMAN){
                ((HumanPlayer) player).setMatchesPlayed(((HumanPlayer) player).getMatchesPlayed()+1);
                if(player==players.get(winner)){
                    ((HumanPlayer) player).setMatchesWon(((HumanPlayer) player).getMatchesWon()+1);
                }
            }

        }
    }
}
