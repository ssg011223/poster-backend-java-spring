package com.codecool.poster.service;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.UserRole;
import com.codecool.poster.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String register(Person person) {
        if (person.getUsername() == null || person.getEmail() == null || person.getBirthDate() == null || person.getPassword() == null)
            return "Registration failed!";

        if (person.getBirthDate().isAfter(LocalDate.from(LocalDate.now()))) {
            return "Birth date is not valid";
        }

        boolean emailExists = personRepository
                .findByEmail(person.getEmail())
                .isPresent();

        boolean usernameExists = personRepository
                .findByUsername(person.getUsername())
                .isPresent();

        if (emailExists || usernameExists)
            return "Email or username already taken!";

        String encodedPassword = bCryptPasswordEncoder.encode(person.getPassword());

        person.setPassword(encodedPassword);

        personRepository.save(person);

        return "success";
    }
}
