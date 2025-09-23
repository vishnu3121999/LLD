package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Specialization;
import org.example.enums.UserType;

import java.util.UUID;

@Data
public class Doctor extends User{

    Specialization specialization;

    public Doctor(String id, String name, UserType userType, Specialization specialization) {
        super(id, name, userType);
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "specialization=" + specialization +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userType=" + userType +
                '}';
    }
}
