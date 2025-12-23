package strategy;

import database.State;
import models.Direction;
import models.Elevator;
import models.ElevatorState;


// this ignores user's direction choice when assigning elevator
public class MinWaitTimeElevetorStrategy implements ElevatorSelectionStrategy{
    @Override
    public String getElevator(State state, int floor, Direction direction) {
        int minDist = Integer.MAX_VALUE;
        String minEleId = null;
        for(var ele : state.getElevatorHashMap().values()){
            var dir = ele.getDirection();
            if(ele.getElevatorState()!= ElevatorState.MAINTENANCE){
                if((dir==Direction.UP && floor>ele.getCurrFloor()) || (dir==Direction.DOWN && floor<ele.getCurrFloor())){
                    int dist = Math.abs(floor-ele.getCurrFloor());
                    if(dist<minDist){
                        minDist=dist;
                        minEleId=ele.getId();
                    }
                }
            }
        }

        // random assignment if none found in the dir ele is moving.
        if(minEleId==null)minEleId=state.getElevatorHashMap().keySet().iterator().next();
        return minEleId;

    }
}
