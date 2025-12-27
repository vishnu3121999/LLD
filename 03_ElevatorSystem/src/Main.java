import database.DataStore;
import models.Direction;
import models.Elevator;
import models.ElevatorState;
import observer.HallDisplay;
import services.ServiceFacade;
import strategy.movement.SameDirectionStrategy;
import strategy.selection.MinSeekTimeStrategy;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Elevator System Simulation Started ===\n");
        
        DataStore dataStore = new DataStore();
        ServiceFacade api = new ServiceFacade(dataStore, new MinSeekTimeStrategy());

        // Initialize elevators at different starting floors
        var e1 = api.addElevator(10, new SameDirectionStrategy());
        var e2 = api.addElevator(10, new SameDirectionStrategy());
        
        // Set initial floors
        dataStore.getElevator(e1).setCurrFloor(2);
        dataStore.getElevator(e2).setCurrFloor(5);

        dataStore.getElevator(e1).addObserver(new HallDisplay());
        dataStore.getElevator(e2).addObserver(new HallDisplay());

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            try {
                dataStore.getElevator(e1).start();
            } catch (Exception e) {
                System.err.println("Elevator 1 error: " + e.getMessage());
            }
        });
        executorService.submit(() -> {
            try {
                dataStore.getElevator(e2).start();
            } catch (Exception e) {
                System.err.println("Elevator 2 error: " + e.getMessage());
            }
        });

        // Give elevators time to start
        Thread.sleep(1000);

        // Simulate realistic scenarios
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100);
        
        // Scenario 1: Morning rush - people going up from ground floor
        System.out.println("\n[8:00 AM] Morning rush hour begins...");
        scheduleRequest(scheduler, api, dataStore, 0, Direction.UP, 0); // Person 1 at floor 0, wants to go up
        scheduleRequest(scheduler, api, dataStore, 0, Direction.UP, 2); // Person 2 at floor 0, wants to go up
        scheduleRequest(scheduler, api, dataStore, 0, Direction.UP, 4); // Person 3 at floor 0, wants to go up
        
        // Scenario 2: Mid-morning - mixed traffic
        scheduleRequest(scheduler, api, dataStore, 3, Direction.DOWN, 8); // Person at floor 3 wants to go down
        scheduleRequest(scheduler, api, dataStore, 7, Direction.UP, 10); // Person at floor 7 wants to go up
        
        // Scenario 3: Lunch time - people going down
        scheduleRequest(scheduler, api, dataStore, 10, Direction.DOWN, 15); // Person at floor 10 wants to go down
        scheduleRequest(scheduler, api, dataStore, 8, Direction.DOWN, 17); // Person at floor 8 wants to go down
        
        // Scenario 4: Afternoon - random requests
        scheduleRequest(scheduler, api, dataStore, 1, Direction.UP, 20); // Person at floor 1 wants to go up
        scheduleRequest(scheduler, api, dataStore, 6, Direction.DOWN, 22); // Person at floor 6 wants to go down
        scheduleRequest(scheduler, api, dataStore, 4, Direction.UP, 24); // Person at floor 4 wants to go up
        
        // Scenario 5: Evening rush - people going down
        scheduleRequest(scheduler, api, dataStore, 15, Direction.DOWN, 28); // Person at floor 15 wants to go down
        scheduleRequest(scheduler, api, dataStore, 12, Direction.DOWN, 30); // Person at floor 12 wants to go down
        scheduleRequest(scheduler, api, dataStore, 9, Direction.DOWN, 32); // Person at floor 9 wants to go down

        // Keep simulation running
        Thread.sleep(60000); // Run for 1 minute (elevators move faster now)
        
        System.out.println("\n=== Simulation Complete - Shutting down ===");
        scheduler.shutdown();
        executorService.shutdown();
        
        // Wait for tasks to complete
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }

    private static void scheduleRequest(ScheduledExecutorService scheduler, ServiceFacade api, DataStore dataStore,
                                       int requestFloor, Direction direction, 
                                       int delaySeconds) {
        int personId = random.nextInt(100);
        scheduler.schedule(() -> {
            // Generate destination floor first (person knows where they want to go)
            int destinationFloor = generateDestinationFloor(requestFloor, direction);

            
            // Request elevator (adds pickup floor as stop)
            String elevatorId = api.requestElevator(requestFloor, direction);
            Elevator elevator = dataStore.getElevator(elevatorId);
            System.out.println(String.format("[Time: %ds] Person-%d at floor %d requests elevator going %s (destination: floor %d); Assigned elevator-%s",
                    delaySeconds, personId,requestFloor, direction, destinationFloor,elevatorId.substring(0, 8) + "..."));
            while (elevator.getCurrFloor()!=requestFloor){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            elevator.setElevatorState(ElevatorState.IDLE);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

                System.out.println(String.format("         → Person-%d enters elevator %s at floor %d and selects floor %d",personId,
                        elevatorId.substring(0, 8) + "...", requestFloor, destinationFloor));
                api.selectFloor(elevatorId, destinationFloor);

            
        }, delaySeconds, TimeUnit.SECONDS);
    }

    private static int generateDestinationFloor(int currentFloor, Direction direction) {
        // Generate a realistic destination floor based on direction
        if (direction == Direction.UP) {
            // Going up - destination should be above current floor
            return currentFloor + random.nextInt(10) + 1; // 1-10 floors above
        } else {
            // Going down - destination should be below current floor
            return Math.max(0, currentFloor - random.nextInt(currentFloor + 1)); // Down to ground floor
        }
    }
}



