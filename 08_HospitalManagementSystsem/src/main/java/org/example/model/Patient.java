package org.example.model;


import lombok.Data;
import org.example.enums.UserType;

import java.util.UUID;

@Data
public class Patient extends User {
    public Patient(String id, String name, UserType userType) {
        super(id, name, userType);
    }
}
