package org.example.database;

import lombok.Data;
import lombok.ToString;
import org.example.model.Booking;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.Slot;

import java.util.HashMap;
import java.util.Map;



public class State {
    Map<String, Doctor> docters;
    Map<String, Patient> patients;
    Map<String, Slot> slots;
    Map<String, Booking> bookings;

    public State(){
        docters = new HashMap<>();
        patients = new HashMap<>();
        slots = new HashMap<>();
        bookings = new HashMap<>();
    }

    public Map<String, Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Map<String, Booking> bookings) {
        this.bookings = bookings;
    }

    public Map<String, Doctor> getDocters() {
        return docters;
    }

    public void setDocters(Map<String, Doctor> docters) {
        this.docters = docters;
    }

    public Map<String, Patient> getPatients() {
        return patients;
    }

    public void setPatients(Map<String, Patient> patients) {
        this.patients = patients;
    }

    public Map<String, Slot> getSlots() {
        return slots;
    }

    public void setSlots(Map<String, Slot> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return "State{" +
                "bookings=" + bookings + "\n" +
                ", docters=" + docters + "\n" +
                ", patients=" + patients + "\n" +
                ", slots=" + slots +
                '}';
    }
}
