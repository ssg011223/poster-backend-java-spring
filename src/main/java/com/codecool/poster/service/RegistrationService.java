package com.codecool.poster.service;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.UserRole;
import com.codecool.poster.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@AllArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(Person person) {
        if (person.getUsername() == null || person.getEmail() == null || person.getBirthDate() == null || person.getPassword() == null)
            throw new IllegalArgumentException("Person details can not be null!");

        if (person.getBirthDate().isAfter(LocalDate.from(LocalDate.now())))
            throw new IllegalArgumentException("Date can not be after today!");

        boolean emailExists = personRepository
                .findByEmail(person.getEmail())
                .isPresent();

        boolean usernameExists = personRepository
                .findByUsername(person.getUsername())
                .isPresent();

        if (emailExists || usernameExists)
            throw new IllegalArgumentException("Email or username already taken!");

        String encodedPassword = bCryptPasswordEncoder.encode(person.getPassword());

        person.setPassword(encodedPassword);
        person.setUserRole(UserRole.USER);

        personRepository.save(person);
    }
}
