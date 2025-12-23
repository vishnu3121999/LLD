package services;

import database.State;
import models.Direction;
import models.Elevator;
import models.ElevatorState;
import strategy.ElevatorSelectionStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceFacade {

    State state;
    ElevatorSelectionStrategy elevatorSelectionStrategy;
    ExecutorService executorService = Executors.newCachedThreadPool();

    ServiceFacade(State state,ElevatorSelectionStrategy elevatorSelectionStrategy){
        this.state=state;
        this.elevatorSelectionStrategy = elevatorSelectionStrategy;
    }

    // Admin APIs
    public void start(String elevatorId){
        Elevator elevator = state.getElevatorHashMap().get(elevatorId);
        executorService.submit(new Move(elevator));

    }
    public void stop(String elevatorId){
        Elevator elevator = state.getElevatorHashMap().get(elevatorId);
        elevator.setElevatorState(ElevatorState.MAINTENANCE);
    }

    // User APIs
    // called from hall
    public void requestElevator(int floor, Direction direction){
        String id = elevatorSelectionStrategy.getElevator(state,floor,direction);
        state.getElevatorById(id).addStop(floor);
    }
    //called from inside the elevator
    public void selectFloor(String eleId, int floor){
        state.getElevatorById(eleId).addStop(floor);
    }




}

class Move implements Runnable{

    Elevator elevator;

    Move(Elevator elevator){this.elevator=elevator;}

    @Override
    public void run() {
        while(elevator.getElevatorState()!= ElevatorState.MAINTENANCE){
            int nextStop = elevator.getNextStop();
            if(nextStop==-1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            System.out.println("Going to "+nextStop);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Reached "+nextStop);
            elevator.setCurrFloor(nextStop);
            elevator.removeStop(nextStop);
        }
    }
}
