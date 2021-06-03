package com.codecool.poster.controller;

import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PeopleController {

    private final PersonService personService;

    @GetMapping("/follow-sidebar")
    public ResponseEntity getFollowSidebarPeople(@CookieValue("token") String token) { return personService.getAllPeopleExpectLoggedPerson(token); }
}
