package org.example.services;

import org.example.database.State;
import org.example.enums.SeatStatus;
import org.example.models.Show;
import org.example.models.Ticket;

import java.util.List;
import java.util.UUID;

public class BookingService {
    State state;

    public BookingService(State state) {
        this.state = state;
    }

    String bookTicket(String userId, String showId, List<String> seatIds){

        if(!checkIfAllSeatsAreFree(showId,seatIds))return null;

        Ticket ticket = new Ticket(UUID.randomUUID().toString(),showId,seatIds);
        state.getTickets().put(ticket.getId(),ticket);

        return ticket.getId();
    }

    private boolean checkIfAllSeatsAreFree(String showId, List<String> seatIds) {
        Show show = state.getShows().get(showId);
        for(String seatId:seatIds){
            if(state.getSeats().get(seatId).getSeatStatus()!= SeatStatus.FREE)return false;
        }
        return true;
    }
}
