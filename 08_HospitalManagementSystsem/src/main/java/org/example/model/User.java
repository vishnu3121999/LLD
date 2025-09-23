package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.UserType;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    String id;
    String name;
    UserType userType;

    public User(){}

}
