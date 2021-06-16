package com.codecool.poster.controller;

import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
@CrossOrigin(origins = {"${cross.origin.port.number}"})
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity getPerson(@CookieValue("token") String token) { return personService.getPersonDetails(token); }

    @GetMapping("/{id}")
    public ResponseEntity getPersonById(@CookieValue("token") String token,
                                    @PathVariable("id") String profileId) {
        return personService.getPersonById(profileId, token); }

    @PostMapping("/{id}")
    public void follow(@PathVariable("id") String followedId,
                                        @RequestParam String followerId) {
        personService.followPerson(followedId, followerId);
    }
}
