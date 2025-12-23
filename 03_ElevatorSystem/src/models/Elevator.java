package models;

import observer.ElevatorObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Elevator {
    String id;
    int capacity;
    ElevatorState elevatorState;
    Direction direction;
    int currFloor;
    TreeSet<Integer> stops;
//    HashSet<Integer> allowedFloors;        --> additional feature
    List<ElevatorObserver> observers = new ArrayList<>();


    public Elevator(String id, int capacity, int currFloor) {
        this.id = id;
        this.capacity = capacity;
        this.currFloor = currFloor;
        stops = new TreeSet<>();
        elevatorState = ElevatorState.IDLE;
        direction = Direction.NONE;
        AtomicInteger i = new AtomicInteger();
    }

    public void addStop(int floor) {
        stops.add(floor);
    }

    public void removeStop(int floor) {
        stops.remove(floor);
    }

    public int getNextStop() {
        if (direction == Direction.UP) {
            return stops.ceiling(currFloor);
        } else if (direction == Direction.DOWN) {
            return stops.floor(currFloor);
        } else return -1;
    }

    // --- Observer Pattern Methods ---
    public void addObserver(ElevatorObserver observer) {
        observers.add(observer);
        observer.update(this); // Send initial state
    }

    public void notifyObservers() {
        for (ElevatorObserver observer : observers) {
            observer.update(this);
        }
    }


    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ElevatorState getElevatorState() {
        return elevatorState;
    }

    public void setElevatorState(ElevatorState elevatorState) {
        this.elevatorState = elevatorState;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getCurrFloor() {
        return currFloor;
    }

    public void setCurrFloor(int currFloor) {
        this.currFloor = currFloor;
    }

    public TreeSet<Integer> getStops() {
        return stops;
    }

    public void setStops(TreeSet<Integer> stops) {
        this.stops = stops;
    }

}