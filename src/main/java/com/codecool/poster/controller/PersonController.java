package com.codecool.poster.controller;

import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/profile/{id}")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity getPerson(@CookieValue("token") String token,
                                    @PathVariable("id") String profileId,
                                    HttpServletResponse res) {
        return personService.getPerson(profileId, token); }

    @PostMapping()
    public void follow(@PathVariable("id") String followedId,
                                        @RequestParam String followerId) {
        personService.followPerson(followedId, followerId);
    }
}
