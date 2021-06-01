package com.codecool.poster.service;

import com.codecool.poster.model.Person;
import com.codecool.poster.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@AllArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<String> register(Person person) {
        if (person.getUsername() == null || person.getEmail() == null || person.getBirthDate() == null || person.getPassword() == null)
            return ResponseEntity.badRequest().body("User details can not be null!");

        if (person.getBirthDate().isAfter(LocalDate.from(LocalDate.now())))
            return ResponseEntity.badRequest().body("Date can not be after today!");

        boolean emailExists = personRepository
                .findByEmail(person.getEmail())
                .isPresent();

        boolean usernameExists = personRepository
                .findByUsername(person.getUsername())
                .isPresent();

        if (emailExists || usernameExists)
            return ResponseEntity.badRequest().body("Email or Username already taken!");

        String encodedPassword = bCryptPasswordEncoder.encode(person.getPassword());

        person.setPassword(encodedPassword);

        personRepository.save(person);
        return ResponseEntity.ok("");
    }
}
