package org.example.services;

import org.example.models.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ServiceFacade {

    BookingService bookingService;
    AdminService adminService;
    SearchService searchService;

    public ServiceFacade(AdminService adminService, BookingService bookingService, SearchService searchService) {
        this.adminService = adminService;
        this.bookingService = bookingService;
        this.searchService = searchService;
    }

    public String addTheater(String city , List<String> screens){
        return adminService.addTheater(city,screens);
    }

    public String addScreen(String theaterId, List<String> shows){
        return adminService.addScreen(theaterId, shows);
    }

    public String addShow(String screenId, String movie, String theaterId, LocalDateTime startTime, List<String> seats) {
        return adminService.addShow(screenId,movie,theaterId,startTime,seats);
    }

    public String addSeat(String showId){
        return adminService.addSeat(showId);
    }

    public String bookTicket(String userId, String showId, List<String> seatIds){
        return bookingService.bookTicket(userId,showId,seatIds);
    }

    public List<String> getTheatersByMovieAndCity(String movie,String city){
        return searchService.getTheatersByMovieAndCity(movie,city);
    }

}
