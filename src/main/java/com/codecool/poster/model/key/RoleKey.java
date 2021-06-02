package com.codecool.poster.model.key;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.UserRoleEnum;

import java.io.Serializable;

public class RoleKey implements Serializable {
    private Person person;
    private UserRoleEnum userRole;
}
