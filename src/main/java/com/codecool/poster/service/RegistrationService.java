package com.codecool.poster.service;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final PersonService personService;

    public String register(Person person) {
        return personService.signUpUser(Person.builder()
                .username(person.getUsername())
                .email(person.getEmail())
                .password(person.getPassword())
                .birthDate(person.getBirthDate())
                .userRole(UserRole.USER)
                .registrationDate(LocalDateTime.now())
                .build());
    }
}
