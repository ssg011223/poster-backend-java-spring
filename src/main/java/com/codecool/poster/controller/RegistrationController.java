package com.codecool.poster.controller;

import com.codecool.poster.model.Person;
import com.codecool.poster.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody Person person) {
        registrationService.register(person);
        return ResponseEntity.ok("User Registered");
    }
}
