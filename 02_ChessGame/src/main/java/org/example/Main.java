package org.example;


import org.example.players.EasyAIPlayer;
import org.example.players.HumanPlayer;
import org.example.strategies.Classical;

public class Main {
    public static void main(String[] args) {

        Game game = new Game(new Classical(),new HumanPlayer(),new EasyAIPlayer());

        game.start();
    }
}