package strategy;

import database.State;
import models.Direction;

public interface ElevatorSelectionStrategy {

    String getElevator(State state,int floor, Direction direction);
}
