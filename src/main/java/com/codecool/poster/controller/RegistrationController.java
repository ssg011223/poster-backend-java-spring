package com.codecool.poster.controller;

import com.codecool.poster.model.Person;
import com.codecool.poster.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody Person person) { return registrationService.register(person); }
}