package org.example.database;

import lombok.Data;
import org.example.models.*;

import java.util.HashMap;
import java.util.Map;

@Data
public class State {

    // Tables
    Map<String, Movie> movies;
    Map<String, Theater> theaters;
    Map<String, Ticket> tickets;
    Map<String, Show> shows;
    Map<String, Screen> screens;
    Map<String, Seat> seats;

    public State(){
        movies = new HashMap<>();
        theaters = new HashMap<>();
        tickets = new HashMap<>();
        shows = new HashMap<>();
        screens = new HashMap<>();
        seats = new HashMap<>();
    }

}
