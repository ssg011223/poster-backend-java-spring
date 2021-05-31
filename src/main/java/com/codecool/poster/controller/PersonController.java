package com.codecool.poster.controller;

import com.codecool.poster.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/profile/{followedId}")
public class PersonController {

    private final PersonService personService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "")
    public void follow(@PathVariable("followedId") String followedId,
                                        @RequestParam String followerId) {
        personService.followPerson(followedId, followerId);
    }
}
