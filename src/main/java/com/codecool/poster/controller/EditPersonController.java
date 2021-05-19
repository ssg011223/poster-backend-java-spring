package com.codecool.poster.controller;

import com.codecool.poster.model.Person;
import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/settings/profile")
public class EditPersonController {

    private final PersonService personService;

    @PostMapping
    public String editPerson(@RequestBody Person person) {
        return "";
    }
}
