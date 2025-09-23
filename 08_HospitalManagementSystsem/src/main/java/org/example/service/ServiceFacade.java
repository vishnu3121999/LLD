package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.database.State;
import org.example.enums.SlotStatus;
import org.example.enums.Specialization;
import org.example.enums.UserType;
import org.example.factory.UserFactory;
import org.example.model.Booking;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.Slot;
import org.example.strategies.SearchStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ServiceFacade {

    State state;
    SearchStrategy searchStrategy;

    public String registerDoctor(String name, Specialization specialization){
        String id = UUID.randomUUID().toString();
        Doctor doctor = UserFactory.createDoctor(id,name,specialization);
        state.getDocters().put(doctor.getId(),doctor);
        return id;
    }
    public String registerPatient(String name){
        String id = UUID.randomUUID().toString();
        Patient patient = UserFactory.createPatient(id,name);
        state.getPatients().put(id,patient);
        return patient.getId();
    }

    public String addSlot(String doctorId, LocalDateTime startTime){
        String id = UUID.randomUUID().toString();
        Specialization specialization = state.getDocters().get(doctorId).getSpecialization();
        Slot slot = new Slot(id,doctorId,startTime, SlotStatus.FREE,specialization);
        state.getSlots().put(id,slot);
        return id;
    }

    public List<Slot> searchSlotBySpecialization(Specialization specialization){
        return searchStrategy.search(state.getSlots(),specialization);
    }

    public String bookSlot(String patientId, String slotId){
        if(state.getSlots().get(slotId).getSlotStatus()==SlotStatus.BOOKED){
            throw new RuntimeException("Slot not available");
        }

        String id = UUID.randomUUID().toString();
        Booking booking = new Booking(id,patientId,slotId);
        state.getBookings().put(id,booking);
        state.getSlots().get(slotId).setSlotStatus(SlotStatus.BOOKED);
        return id;
    }




}
