package org.example.strategies;

import org.example.enums.Specialization;
import org.example.model.Slot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RankByStartTimeStrategy implements SearchStrategy{
    @Override
    public List<Slot> search(Map<String, Slot> slots, Specialization specialization) {
        ArrayList<Slot> list = new ArrayList<>();
        for(var id:slots.keySet()){
            Slot slot = slots.get(id);
            if(slot.getSpecialization()==specialization)list.add(slot);
        }

        Collections.sort(list,(a,b)->(a.getStartTime().isBefore(b.getStartTime())?-1:1));
        return list;
    }
}
