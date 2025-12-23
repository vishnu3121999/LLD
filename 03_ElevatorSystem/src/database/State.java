package database;

import models.Elevator;

import java.util.HashMap;

public class State {
    HashMap<String, Elevator> elevatorHashMap = new HashMap<>();



    public Elevator getElevatorById(String id){
        return getElevatorHashMap().get(id);
    }



    // getters & setters

    public HashMap<String, Elevator> getElevatorHashMap() {
        return elevatorHashMap;
    }

    public void setElevatorHashMap(HashMap<String, Elevator> elevatorHashMap) {
        this.elevatorHashMap = elevatorHashMap;
    }
}
