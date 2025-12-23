package observer;

import models.Elevator;

public class HallDisplay implements ElevatorObserver {

    @Override
    public void update(Elevator elevator) {
        System.out.println("curr floor= "+elevator.getCurrFloor()+", Direction= "+ elevator.getDirection());
    }
}
