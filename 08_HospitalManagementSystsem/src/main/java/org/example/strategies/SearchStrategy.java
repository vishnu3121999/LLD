package org.example.strategies;

import org.example.enums.Specialization;
import org.example.model.Slot;

import java.util.List;
import java.util.Map;

public interface SearchStrategy {
    List<Slot> search(Map<String,Slot> slots, Specialization specialization);
}
