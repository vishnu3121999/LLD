package org.example.factory;

import org.example.enums.Specialization;
import org.example.enums.UserType;
import org.example.model.Doctor;
import org.example.model.Patient;

public class UserFactory {

    public static Doctor createDoctor(String id,String name, Specialization specialization) {
        return new Doctor(id,name, UserType.DOCTOR, specialization);
    }

    public static Patient createPatient(String id,String name){
        return new Patient(id,name,UserType.PATIENT);
    }
}
