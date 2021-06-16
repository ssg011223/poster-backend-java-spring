package com.codecool.poster.service;

import com.codecool.poster.model.Person;
import com.codecool.poster.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;


    private final Person person = new Person();

    @BeforeEach
    public void personSetup() {
        person.setUsername("username");
        person.setEmail("email@email.com");
        person.setPassword("password");
        person.setBirthDate(LocalDate.of(2000, 1, 1));
    }

    @Test
    void registerShouldReturnResponseEntityOk() {
        when(personRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(personRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("");

        assertEquals(ResponseEntity.ok(""), registrationService.register(person));
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void registerShouldReturnResponseEntityBadRequestUserDetailsCannotBeNull() {
        ResponseEntity expected = ResponseEntity.badRequest().body("User details can not be null!");

        assertEquals(expected, registrationService.register(new Person()));
    }

    @Test
    void registerShouldReturnResponseEntityBadRequestBirthDateCannotBeAfterToday() {
        ResponseEntity expected = ResponseEntity.badRequest().body("Date can not be after today!");

        person.setBirthDate(LocalDate.now().plusDays(1));

        assertEquals(expected, registrationService.register(person));
    }

    @Test
    void registerShouldReturnResponseEntityBadRequestEmailTaken() {
        when(personRepository.findByEmail(anyString())).thenReturn(Optional.of(new Person()));
        when(personRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertEquals(ResponseEntity.badRequest().body("Email or Username already taken!"), registrationService.register(person));
    }

    @Test
    void registerShouldReturnResponseEntityBadRequestUsernameTaken() {
        when(personRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(personRepository.findByUsername(anyString())).thenReturn(Optional.of(new Person()));

        assertEquals(ResponseEntity.badRequest().body("Email or Username already taken!"), registrationService.register(person));
    }

}