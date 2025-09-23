package org.example;

import org.example.database.State;
import org.example.enums.Specialization;
import org.example.service.ServiceFacade;
import org.example.strategies.RankByStartTimeStrategy;
import org.example.strategies.SearchStrategy;



public class Main {
    public static void main(String[] args) {

        State state = new State();
        SearchStrategy searchStrategy = new RankByStartTimeStrategy();
        ServiceFacade serviceFacade = new ServiceFacade(state,searchStrategy);

        // create users
        String doctor1 = serviceFacade.registerDoctor("D1", Specialization.CARDIOLOGIST);
        String doctor2 = serviceFacade.registerDoctor("D2", Specialization.DERMATOLOGIST);

        String patient1 = serviceFacade.registerPatient("P1");
        String patient2 = serviceFacade.registerPatient("P2");

        var ans = state.toString();
        System.out.println(ans);

    }
}



