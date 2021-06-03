package com.codecool.poster.service;

import com.codecool.poster.model.Person;
import com.codecool.poster.model.UserRole;
import com.codecool.poster.model.UserRoleEnum;
import com.codecool.poster.repository.PersonRepository;
import com.codecool.poster.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

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
        person.setRegistrationDate(LocalDateTime.now());
        person.setDescription("");

        UserRole role = new UserRole(person, UserRoleEnum.ROLE_USER);

        person.setRoles(List.of(role));

        personRepository.save(person);
        return ResponseEntity.ok("");
    }
}
