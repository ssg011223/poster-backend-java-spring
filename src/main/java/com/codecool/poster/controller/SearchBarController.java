package com.codecool.poster.controller;

import com.codecool.poster.model.Person;
import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "search")
public class SearchBarController {

    private final PersonService personService;

    @PostMapping
    @CrossOrigin(origins = "*")
    public Collection<Person> searchPeople(@RequestBody String searchPhrase) { return personService.searchPeople(searchPhrase); }
}
