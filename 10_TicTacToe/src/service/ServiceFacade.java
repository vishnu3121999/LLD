package service;

import database.Repo;
import models.board.Board;
import models.board.BoardType;
import models.board.SimpleBoard;
import models.game.Game;
import models.game.GameType;
import models.game.NoTimerGame;
import models.player.Player;

import java.util.List;
import java.util.UUID;

public class ServiceFacade {

    Repo repo;

    public ServiceFacade(Repo repo) {
        this.repo = repo;
    }


    String startGame(int noOfPlayers, List<String> playerIds, GameType gameType, BoardType boardType, int boardSize){
        String gameId = UUID.randomUUID().toString();
        List<Player> players = playerIds.stream().map((id)->repo.getPlayerMap().get(id)).toList();


        //Replace with Factory/Abstract-Factory

        Board board = switch (boardType){
            case SIMPLE_BOARD -> new SimpleBoard(boardSize);
        };
        Game game = switch (gameType){
            case NO_TIMER_GAME -> new NoTimerGame(gameId,noOfPlayers,players,board);
        };

        repo.getGameMap().put(gameId,game);

        if(playerIds.size()==noOfPlayers)game.start();

        return gameId;
    }

    void joinGame(String gameId, String playerId){
        Player player = repo.getPlayerMap().get(playerId);
        Game game = repo.getGameMap().get(gameId);
        game.getPlayers().add(player);

        if(game.getPlayers().size()==game.getNoOfPlayers())game.start();
    }


}
